package net.minecraftforge.client.model.pipeline;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumFacing;

// advantages: non-fixed-length vertex format, no overhead of packing and unpacking attributes to transform the model
// disadvantages: (possibly) larger memory footprint, overhead on packing the attributes at the final rendering stage
public class UnpackedBakedQuad extends BakedQuad
{
    protected final float[][][] unpackedData;
    protected final VertexFormat format;
    protected boolean packed = false;

    public UnpackedBakedQuad(float[][][] unpackedData, int tint, EnumFacing orientation, TextureAtlasSprite texture, boolean applyDiffuseLighting, VertexFormat format)
    {
        super(new int[format.getNextOffset() /* / 4 * 4 */], tint, orientation, texture, applyDiffuseLighting, format);
        this.unpackedData = unpackedData;
        this.format = format;
    }

    @Override
    public int[] getVertexData()
    {
        if(!packed)
        {
            packed = true;
            for(int v = 0; v < 4; v++)
            {
                for(int e = 0; e < format.getElementCount(); e++)
                {
                    LightUtil.pack(unpackedData[v][e], vertexData, format, v, e);
                }
            }
        }
        /**
         * Joined 4 vertex records, each stores packed data according to the VertexFormat of the quad. Vanilla minecraft
         * uses DefaultVertexFormats.BLOCK, Forge uses (usually) ITEM, use BakedQuad.getFormat() to get the correct
         * format.
         */
        return vertexData;
    }

    @Override
    public void pipe(IVertexConsumer consumer)
    {
        int[] eMap = LightUtil.mapFormats(consumer.getVertexFormat(), format);

        if(hasTintIndex())
        {
            consumer.setQuadTint(getTintIndex());
        }
        consumer.setQuadOrientation(getFace());
        for(int v = 0; v < 4; v++)
        {
            for(int e = 0; e < consumer.getVertexFormat().getElementCount(); e++)
            {
                if(eMap[e] != format.getElementCount())
                {
                    consumer.put(e, unpackedData[v][eMap[e]]);
                }
                else
                {
                    consumer.put(e);
                }
            }
        }
    }

    public static class Builder implements IVertexConsumer
    {
        private final VertexFormat format;
        private final float[][][] unpackedData;
        private int tint = -1;
        private EnumFacing orientation;
        private TextureAtlasSprite texture;
        private boolean applyDiffuseLighting = true;

        private int vertices = 0;
        private int elements = 0;
        private boolean full = false;
        private boolean contractUVs = false;

        public Builder(VertexFormat format)
        {
            this.format = format;
            unpackedData = new float[4][format.getElementCount()][4];
        }

        public VertexFormat getVertexFormat()
        {
            return format;
        }

        public void setContractUVs(boolean value)
        {
            this.contractUVs = value;
        }
        public void setQuadTint(int tint)
        {
            this.tint = tint;
        }

        public void setQuadOrientation(EnumFacing orientation)
        {
            this.orientation = orientation;
        }

        public void setTexture(TextureAtlasSprite texture)
        {
            this.texture = texture;
        }

        public void setApplyDiffuseLighting(boolean diffuse)
        {
            this.applyDiffuseLighting = diffuse;
        }

        public void put(int element, float... data)
        {
            for(int i = 0; i < 4; i++)
            {
                if(i < data.length)
                {
                    unpackedData[vertices][element][i] = data[i];
                }
                else
                {
                    unpackedData[vertices][element][i] = 0;
                }
            }
            elements++;
            if(elements == format.getElementCount())
            {
                vertices++;
                elements = 0;
            }
            if(vertices == 4)
            {
                full = true;
            }
        }

        private final float eps = 1f / 0x100;

        public UnpackedBakedQuad build()
        {
            if(!full)
            {
                throw new IllegalStateException("not enough data");
            }
            if(contractUVs)
            {
                float tX = texture.getOriginX() / texture.getMinU();
                float tY = texture.getOriginY() / texture.getMinV();
                float tS = tX > tY ? tX : tY;
                float ep = 1f / (tS * 0x100);
                int uve = 0;
                while(uve < format.getElementCount())
                {
                    VertexFormatElement e = format.getElement(uve);
                    if(e.getUsage() == VertexFormatElement.EnumUsage.UV && e.getIndex() == 0)
                    {
                        break;
                    }
                    uve++;
                }
                if(uve == format.getElementCount())
                {
                    throw new IllegalStateException("Can't contract UVs: format doesn't contain UVs");
                }
                float[] uvc = new float[4];
                for(int v = 0; v < 4; v++)
                {
                    for(int i = 0; i < 4; i++)
                    {
                        uvc[i] += unpackedData[v][uve][i] / 4;
                    }
                }
                for(int v = 0; v < 4; v++)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        float uo = unpackedData[v][uve][i];
                        float un = uo * (1 - eps) + uvc[i] * eps;
                        float ud = uo - un;
                        float aud = ud;
                        if(aud < 0) aud = -aud;
                        if(aud < ep) // not moving a fraction of a pixel
                        {
                            float udc = uo - uvc[i];
                            if(udc < 0) udc = -udc;
                            if(udc < 2 * ep) // center is closer than 2 fractions of a pixel, don't move too close
                            {
                                un = (uo + uvc[i]) / 2;
                            }
                            else // move at least by a fraction
                            {
                                un = uo + (ud < 0 ? ep : -ep);
                            }
                        }
                        unpackedData[v][uve][i] = un;
                    }
                }
            }
            return new UnpackedBakedQuad(unpackedData, tint, orientation, texture, applyDiffuseLighting, format);
        }
    }
}