package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecorator
{
    public boolean field_185425_a;
    public BlockPos field_180294_c;
    public ChunkProviderSettings chunkProviderSettings;
    /** The clay generator. */
    public WorldGenerator clayGen = new WorldGenClay(4);
    /** The sand generator. */
    public WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
    /** The gravel generator. */
    public WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
    /** The dirt generator. */
    public WorldGenerator dirtGen;
    public WorldGenerator gravelGen;
    public WorldGenerator graniteGen;
    public WorldGenerator dioriteGen;
    public WorldGenerator andesiteGen;
    public WorldGenerator coalGen;
    public WorldGenerator ironGen;
    /** Field that holds gold WorldGenMinable */
    public WorldGenerator goldGen;
    public WorldGenerator redstoneGen;
    public WorldGenerator diamondGen;
    /** Field that holds Lapis WorldGenMinable */
    public WorldGenerator lapisGen;
    public WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
    /** Field that holds mushroomBrown WorldGenFlowers */
    public WorldGenerator mushroomBrownGen = new WorldGenBush(Blocks.brown_mushroom);
    /** Field that holds mushroomRed WorldGenFlowers */
    public WorldGenerator mushroomRedGen = new WorldGenBush(Blocks.red_mushroom);
    /** Field that holds big mushroom generator */
    public WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
    /** Field that holds WorldGenReed */
    public WorldGenerator reedGen = new WorldGenReed();
    /** Field that holds WorldGenCactus */
    public WorldGenerator cactusGen = new WorldGenCactus();
    /** The water lily generation! */
    public WorldGenerator waterlilyGen = new WorldGenWaterlily();
    /** Amount of waterlilys per chunk. */
    public int waterlilyPerChunk;
    /** The number of trees to attempt to generate per chunk. Up to 10 in forests, none in deserts. */
    public int treesPerChunk;
    /**
     * The number of yellow flower patches to generate per chunk. The game generates much less than this number, since
     * it attempts to generate them at a random altitude.
     */
    public int flowersPerChunk = 2;
    /** The amount of tall grass to generate per chunk. */
    public int grassPerChunk = 1;
    /** The number of dead bushes to generate per chunk. Used in deserts and swamps. */
    public int deadBushPerChunk;
    /**
     * The number of extra mushroom patches per chunk. It generates 1/4 this number in brown mushroom patches, and 1/8
     * this number in red mushroom patches. These mushrooms go beyond the default base number of mushrooms.
     */
    public int mushroomsPerChunk;
    /** The number of reeds to generate per chunk. Reeds won't generate if the randomly selected placement is unsuitable. */
    public int reedsPerChunk;
    /** The number of cactus plants to generate per chunk. Cacti only work on sand. */
    public int cactiPerChunk;
    /** The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater. */
    public int sandPerChunk = 1;
    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater. There
     * appear to be two separate fields for this.
     */
    public int sandPerChunk2 = 3;
    /** The number of clay patches to generate per chunk. Only generates when part of it is underwater. */
    public int clayPerChunk = 1;
    /** Amount of big mushrooms per chunk */
    public int bigMushroomsPerChunk;
    /** True if decorator should generate surface lava & water */
    public boolean generateLakes = true;

    public void decorate(World worldIn, Random random, BiomeGenBase biome, BlockPos pos)
    {
        if (this.field_185425_a)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).func_177864_b();
            this.field_180294_c = pos;
            this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
            this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
            this.graniteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
            this.dioriteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
            this.andesiteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
            this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
            this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
            this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
            this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
            this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
            this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
            this.genDecorations(biome, worldIn, random);
            this.field_185425_a = false;
        }
    }

    protected void genDecorations(BiomeGenBase biomeGenBaseIn, World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, field_180294_c));
        this.generateOres(worldIn, random);

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND))
        for (int i = 0; i < this.sandPerChunk2; ++i)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.sandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.field_180294_c.add(j, 0, k)));
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY))
        for (int i1 = 0; i1 < this.clayPerChunk; ++i1)
        {
            int l1 = random.nextInt(16) + 8;
            int i6 = random.nextInt(16) + 8;
            this.clayGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.field_180294_c.add(l1, 0, i6)));
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND_PASS2))
        for (int j1 = 0; j1 < this.sandPerChunk; ++j1)
        {
            int i2 = random.nextInt(16) + 8;
            int j6 = random.nextInt(16) + 8;
            this.gravelAsSandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.field_180294_c.add(i2, 0, j6)));
        }

        int k1 = this.treesPerChunk;

        if (random.nextInt(10) == 0)
        {
            ++k1;
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
        for (int j2 = 0; j2 < k1; ++j2)
        {
            int k6 = random.nextInt(16) + 8;
            int l = random.nextInt(16) + 8;
            WorldGenAbstractTree worldgenabstracttree = biomeGenBaseIn.genBigTreeChance(random);
            worldgenabstracttree.func_175904_e();
            BlockPos blockpos = worldIn.getHeight(this.field_180294_c.add(k6, 0, l));

            if (worldgenabstracttree.generate(worldIn, random, blockpos))
            {
                worldgenabstracttree.func_180711_a(worldIn, random, blockpos);
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
        for (int k2 = 0; k2 < this.bigMushroomsPerChunk; ++k2)
        {
            int l6 = random.nextInt(16) + 8;
            int k10 = random.nextInt(16) + 8;
            this.bigMushroomGen.generate(worldIn, random, worldIn.getHeight(this.field_180294_c.add(l6, 0, k10)));
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        for (int l2 = 0; l2 < this.flowersPerChunk; ++l2)
        {
            int i7 = random.nextInt(16) + 8;
            int l10 = random.nextInt(16) + 8;
            int j14 = worldIn.getHeight(this.field_180294_c.add(i7, 0, l10)).getY() + 32;

            if (j14 > 0)
            {
                int k17 = random.nextInt(j14);
                BlockPos blockpos1 = this.field_180294_c.add(i7, k17, l10);
                BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeGenBaseIn.pickRandomFlower(random, blockpos1);
                BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();

                if (blockflower.getDefaultState().getMaterial() != Material.air)
                {
                    this.yellowFlowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
                    this.yellowFlowerGen.generate(worldIn, random, blockpos1);
                }
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
        for (int i3 = 0; i3 < this.grassPerChunk; ++i3)
        {
            int j7 = random.nextInt(16) + 8;
            int i11 = random.nextInt(16) + 8;
            int k14 = worldIn.getHeight(this.field_180294_c.add(j7, 0, i11)).getY() * 2;

            if (k14 > 0)
            {
                int l17 = random.nextInt(k14);
                biomeGenBaseIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.field_180294_c.add(j7, l17, i11));
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
        for (int j3 = 0; j3 < this.deadBushPerChunk; ++j3)
        {
            int k7 = random.nextInt(16) + 8;
            int j11 = random.nextInt(16) + 8;
            int l14 = worldIn.getHeight(this.field_180294_c.add(k7, 0, j11)).getY() * 2;

            if (l14 > 0)
            {
                int i18 = random.nextInt(l14);
                (new WorldGenDeadBush()).generate(worldIn, random, this.field_180294_c.add(k7, i18, j11));
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD))
        for (int k3 = 0; k3 < this.waterlilyPerChunk; ++k3)
        {
            int l7 = random.nextInt(16) + 8;
            int k11 = random.nextInt(16) + 8;
            int i15 = worldIn.getHeight(this.field_180294_c.add(l7, 0, k11)).getY() * 2;

            if (i15 > 0)
            {
                int j18 = random.nextInt(i15);
                BlockPos blockpos4;
                BlockPos blockpos7;

                for (blockpos4 = this.field_180294_c.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7)
                {
                    blockpos7 = blockpos4.down();

                    if (!worldIn.isAirBlock(blockpos7))
                    {
                        break;
                    }
                }

                this.waterlilyGen.generate(worldIn, random, blockpos4);
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM))
        {
        for (int l3 = 0; l3 < this.mushroomsPerChunk; ++l3)
        {
            if (random.nextInt(4) == 0)
            {
                int i8 = random.nextInt(16) + 8;
                int l11 = random.nextInt(16) + 8;
                BlockPos blockpos2 = worldIn.getHeight(this.field_180294_c.add(i8, 0, l11));
                this.mushroomBrownGen.generate(worldIn, random, blockpos2);
            }

            if (random.nextInt(8) == 0)
            {
                int j8 = random.nextInt(16) + 8;
                int i12 = random.nextInt(16) + 8;
                int j15 = worldIn.getHeight(this.field_180294_c.add(j8, 0, i12)).getY() * 2;

                if (j15 > 0)
                {
                    int k18 = random.nextInt(j15);
                    BlockPos blockpos5 = this.field_180294_c.add(j8, k18, i12);
                    this.mushroomRedGen.generate(worldIn, random, blockpos5);
                }
            }
        }

        if (random.nextInt(4) == 0)
        {
            int i4 = random.nextInt(16) + 8;
            int k8 = random.nextInt(16) + 8;
            int j12 = worldIn.getHeight(this.field_180294_c.add(i4, 0, k8)).getY() * 2;

            if (j12 > 0)
            {
                int k15 = random.nextInt(j12);
                this.mushroomBrownGen.generate(worldIn, random, this.field_180294_c.add(i4, k15, k8));
            }
        }

        if (random.nextInt(8) == 0)
        {
            int j4 = random.nextInt(16) + 8;
            int l8 = random.nextInt(16) + 8;
            int k12 = worldIn.getHeight(this.field_180294_c.add(j4, 0, l8)).getY() * 2;

            if (k12 > 0)
            {
                int l15 = random.nextInt(k12);
                this.mushroomRedGen.generate(worldIn, random, this.field_180294_c.add(j4, l15, l8));
            }
        }
        } // End of Mushroom generation
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED))
        {
        for (int k4 = 0; k4 < this.reedsPerChunk; ++k4)
        {
            int i9 = random.nextInt(16) + 8;
            int l12 = random.nextInt(16) + 8;
            int i16 = worldIn.getHeight(this.field_180294_c.add(i9, 0, l12)).getY() * 2;

            if (i16 > 0)
            {
                int l18 = random.nextInt(i16);
                this.reedGen.generate(worldIn, random, this.field_180294_c.add(i9, l18, l12));
            }
        }

        for (int l4 = 0; l4 < 10; ++l4)
        {
            int j9 = random.nextInt(16) + 8;
            int i13 = random.nextInt(16) + 8;
            int j16 = worldIn.getHeight(this.field_180294_c.add(j9, 0, i13)).getY() * 2;

            if (j16 > 0)
            {
                int i19 = random.nextInt(j16);
                this.reedGen.generate(worldIn, random, this.field_180294_c.add(j9, i19, i13));
            }
        }
        } // End of Reed generation
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
        if (random.nextInt(32) == 0)
        {
            int i5 = random.nextInt(16) + 8;
            int k9 = random.nextInt(16) + 8;
            int j13 = worldIn.getHeight(this.field_180294_c.add(i5, 0, k9)).getY() * 2;

            if (j13 > 0)
            {
                int k16 = random.nextInt(j13);
                (new WorldGenPumpkin()).generate(worldIn, random, this.field_180294_c.add(i5, k16, k9));
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS))
        for (int j5 = 0; j5 < this.cactiPerChunk; ++j5)
        {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(this.field_180294_c.add(l9, 0, k13)).getY() * 2;

            if (l16 > 0)
            {
                int j19 = random.nextInt(l16);
                this.cactusGen.generate(worldIn, random, this.field_180294_c.add(l9, j19, k13));
            }
        }

        if (this.generateLakes)
        {
            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE_WATER))
            for (int k5 = 0; k5 < 50; ++k5)
            {
                int i10 = random.nextInt(16) + 8;
                int l13 = random.nextInt(16) + 8;
                int i17 = random.nextInt(248) + 8;

                if (i17 > 0)
                {
                    int k19 = random.nextInt(i17);
                    BlockPos blockpos6 = this.field_180294_c.add(i10, k19, l13);
                    (new WorldGenLiquids(Blocks.flowing_water)).generate(worldIn, random, blockpos6);
                }
            }

            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, field_180294_c, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA))
            for (int l5 = 0; l5 < 20; ++l5)
            {
                int j10 = random.nextInt(16) + 8;
                int i14 = random.nextInt(16) + 8;
                int j17 = random.nextInt(random.nextInt(random.nextInt(240) + 8) + 8);
                BlockPos blockpos3 = this.field_180294_c.add(j10, j17, i14);
                (new WorldGenLiquids(Blocks.flowing_lava)).generate(worldIn, random, blockpos3);
            }
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, field_180294_c));
    }

    /**
     * Generates ores in the current chunk
     */
    protected void generateOres(World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, field_180294_c));
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dirtGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, gravelGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, field_180294_c, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS))
        this.genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, field_180294_c));
    }

    /**
     * Standard ore generation helper. Generates most ores.
     */
    protected void genStandardOre1(World blockCount, Random generator, int minHeight, WorldGenerator maxHeight, int p_76795_5_, int p_76795_6_)
    {
        if (p_76795_6_ < p_76795_5_)
        {
            int i = p_76795_5_;
            p_76795_5_ = p_76795_6_;
            p_76795_6_ = i;
        }
        else if (p_76795_6_ == p_76795_5_)
        {
            if (p_76795_5_ < 255)
            {
                ++p_76795_6_;
            }
            else
            {
                --p_76795_5_;
            }
        }

        for (int j = 0; j < minHeight; ++j)
        {
            BlockPos blockpos = this.field_180294_c.add(generator.nextInt(16), generator.nextInt(p_76795_6_ - p_76795_5_) + p_76795_5_, generator.nextInt(16));
            maxHeight.generate(blockCount, generator, blockpos);
        }
    }

    /**
     * Standard ore generation helper. Generates Lapis Lazuli.
     */
    protected void genStandardOre2(World blockCount, Random generator, int centerHeight, WorldGenerator spread, int p_76793_5_, int p_76793_6_)
    {
        for (int i = 0; i < centerHeight; ++i)
        {
            BlockPos blockpos = this.field_180294_c.add(generator.nextInt(16), generator.nextInt(p_76793_6_) + generator.nextInt(p_76793_6_) + p_76793_5_ - p_76793_6_, generator.nextInt(16));
            spread.generate(blockCount, generator, blockpos);
        }
    }
}