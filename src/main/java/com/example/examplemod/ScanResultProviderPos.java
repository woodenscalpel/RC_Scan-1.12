package com.example.examplemod;

import li.cil.scannable.api.Icons;
import li.cil.scannable.api.prefab.AbstractScanResultProvider;
import li.cil.scannable.api.scanning.ScanResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import scala.reflect.internal.pickling.UnPickler;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class ScanResultProviderPos extends AbstractScanResultProvider {
    public static final ScanResultProviderPos INSTANCE = new ScanResultProviderPos();

    @Override
    public void computeScanResults(Consumer<ScanResult> callback) {
        World world= Minecraft.getMinecraft().player.getEntityWorld();
        int dimension = world.provider.getDimension();

        List<String> data = DataRead(dimension);

        for(String dataentry: data){
            String[] dataentrylist = dataentry.split(",");
            Vec3d testV = new Vec3d(Double.parseDouble(dataentrylist[1]),Double.parseDouble(dataentrylist[2]),Double.parseDouble(dataentrylist[3]));
            callback.accept(new ScanResultCoord(testV,dataentrylist[0]));


        }

        //String[] coordList = {"0_0_0"}; //FIX
       // String[] pos1 = coordList[0].split("_");
       // System.out.println(pos1);
       // Vec3d testV = new Vec3d(Double.parseDouble(pos1[0]),Double.parseDouble(pos1[1]),Double.parseDouble(pos1[2]));
       // System.out.println(testV);
        //BlockPos testcoord = new BlockPos(Integer.parseInt(pos1[0]), Integer.parseInt(pos1[1]),Integer.parseInt(pos1[2]));
        //System.out.println(testcoord);
        //callback.accept(new ScanResultCoord(testV));


    }

    public static List<String> DataRead(int dimension) {
        File fileName = new File(DimensionManager.getCurrentSaveRootDirectory() + File.separator + "data" + File.separator + "RCScan_StructureLocations" + File.separator + dimension + File.separator  + "structures.txt");
        fileName.getParentFile().mkdirs();

        try {
            FileReader fileReader = new FileReader(fileName);
            //FileWriter fileWriter = new FileWriter(fileName,true);
            //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> lines = new ArrayList<String>();
            String line = null;
           // String testline = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
                //System.out.println(line);
            }
            return lines;
           // System.out.println(testline);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void render(Entity entity, List<ScanResult> results, float partialTicks) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        assert player != null;

        final ResourceLocation icon = Icons.INFO;
        double renderPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double renderPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double renderPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        final float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
        final float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

        final Vec3d lookVec = entity.getLook(partialTicks).normalize();
        final Vec3d viewerEyes = entity.getPositionEyes(partialTicks);

        final boolean showDistance = entity.isSneaking();




        //drawBox(player, event.getMatrixStack());
        for(ScanResult result1 : results) {
            ScanResultCoord result = (ScanResultCoord) result1;
                GlStateManager.pushMatrix();
                GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();




                    //AxisAlignedBB area = new AxisAlignedBB(x1,y1,z1,x2,y2,z2);
                    AxisAlignedBB area = result.getRenderBounds();
                    int xlen = (int) area.maxX - (int) area.minX ;
                    int ylen = (int) area.maxY - (int) area.minY ;
                    int zlen = (int) area.maxZ - (int) area.minZ ;
                    area = new AxisAlignedBB(area.minX, area.minY, area.minZ, area.minX + xlen, area.minY + ylen, area.minZ + zlen);

                    //area = area.offset(-x1, -y1, -(z1 + 1));


                    GlStateManager.pushMatrix();
                    GlStateManager.translate(-renderPosX, -renderPosY, -renderPosZ);
                    Color colorRGB = new Color(555555);
                    GL11.glColor4ub((byte) colorRGB.getRed(), (byte) colorRGB.getGreen(), (byte) colorRGB.getBlue(), (byte) 255);


                    GlStateManager.scale(1F, 1F, 1F);

                    GL11.glLineWidth(3F);
                    renderBlockOutline(area);


                    Vec3d resultPos = new Vec3d(area.minX,area.minY,area.minZ);
                    float distance = (float) Math.sqrt(Math.pow(renderPosX-resultPos.x,2)+Math.pow(renderPosY-resultPos.y,2)+Math.pow(renderPosZ-resultPos.z,2));
                    String name = result.name;
                    renderIconLabel(renderPosX, renderPosY, renderPosZ, yaw, pitch, lookVec, viewerEyes, distance, resultPos, icon, name);







            GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
                GlStateManager.popMatrix();


                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GL11.glPopAttrib();
                GlStateManager.popMatrix();
        }
            }

    private static void renderBlockOutline(AxisAlignedBB aabb) {
        Tessellator tessellator = Tessellator.getInstance();

        double ix = aabb.minX;
        double iy = aabb.minY;
        double iz = aabb.minZ;
        double ax = aabb.maxX;
        double ay = aabb.maxY;
        double az = aabb.maxZ;

        tessellator.getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        tessellator.getBuffer().pos(ix, iy, iz).endVertex();
        tessellator.getBuffer().pos(ix, ay, iz).endVertex();

        tessellator.getBuffer().pos(ix, ay, iz).endVertex();
        tessellator.getBuffer().pos(ax, ay, iz).endVertex();

        tessellator.getBuffer().pos(ax, ay, iz).endVertex();
        tessellator.getBuffer().pos(ax, iy, iz).endVertex();

        tessellator.getBuffer().pos(ax, iy, iz).endVertex();
        tessellator.getBuffer().pos(ix, iy, iz).endVertex();

        tessellator.getBuffer().pos(ix, iy, az).endVertex();
        tessellator.getBuffer().pos(ix, ay, az).endVertex();

        tessellator.getBuffer().pos(ix, iy, az).endVertex();
        tessellator.getBuffer().pos(ax, iy, az).endVertex();

        tessellator.getBuffer().pos(ax, iy, az).endVertex();
        tessellator.getBuffer().pos(ax, ay, az).endVertex();

        tessellator.getBuffer().pos(ix, ay, az).endVertex();
        tessellator.getBuffer().pos(ax, ay, az).endVertex();

        tessellator.getBuffer().pos(ix, iy, iz).endVertex();
        tessellator.getBuffer().pos(ix, iy, az).endVertex();

        tessellator.getBuffer().pos(ix, ay, iz).endVertex();
        tessellator.getBuffer().pos(ix, ay, az).endVertex();

        tessellator.getBuffer().pos(ax, iy, iz).endVertex();
        tessellator.getBuffer().pos(ax, iy, az).endVertex();

        tessellator.getBuffer().pos(ax, ay, iz).endVertex();
        tessellator.getBuffer().pos(ax, ay, az).endVertex();

        tessellator.draw();
    }




    private static final class ScanResultCoord implements ScanResult {
        private final Vec3d coord;
        public final String name;

        ScanResultCoord(final Vec3d coord, String name) {
            this.coord = coord;
            this.name = name;
        }

        // --------------------------------------------------------------------- //
        // ScanResult

        @Override
        public Vec3d getPosition() {
            return coord;
        }


        @Override
        public AxisAlignedBB getRenderBounds() {
            return new AxisAlignedBB(new BlockPos(coord));
        }

        public String getName(){return name;};

    }



// --------------------------------------------------------------------- //

}