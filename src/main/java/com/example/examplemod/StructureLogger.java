package com.example.examplemod;

import ivorius.reccomplex.events.StructureGenerationEventLite;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StructureLogger {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onStructureGenerationLitePost(StructureGenerationEventLite.Post event) {
        World world = event.getWorld();
        if(world.isRemote) return;

        StructureBoundingBox boundingBox = event.getBoundingBox();
        String structureName = event.getStructureName();
        //String regionName = convertBoxToRegion(boundingBox);

        int minX = boundingBox.minX;
        int minZ = boundingBox.minZ;
        int maxX = boundingBox.maxX;
        int maxZ = boundingBox.maxZ;
        int minY = boundingBox.minY;
        int maxY = boundingBox.maxY;

        int dimension = world.provider.getDimension();
        System.out.println("LOADING STRUCTURE!"+structureName);
        System.out.println(minX);
        System.out.println(minY);
        System.out.println(minZ);

        DataWrite(dimension,structureName, minX, minY, minZ);

        }
    public static void DataWrite(int dimension, String structureName, int minX, int minY, int minZ) {
        File fileName = new File(DimensionManager.getCurrentSaveRootDirectory() + File.separator + "data" + File.separator + "RCScan_StructureLocations" + File.separator + dimension + File.separator  + "structures.txt");
        fileName.getParentFile().mkdirs();

        try {
            FileWriter fileWriter = new FileWriter(fileName,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(structureName + "," + minX + "," + minY + "," + minZ);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }
    }

