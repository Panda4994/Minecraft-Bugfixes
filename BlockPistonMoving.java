package net.minecraft.block;

public class BlockPistonMoving extends BlockContainer {
   public static final PropertyDirection field_176426_a = BlockPistonExtension.field_176326_a;
   public static final PropertyEnum field_176425_b = BlockPistonExtension.field_176325_b;
   private static final String __OBFID = "CL_00000368";

   // Removed methods that don't change

   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
      TileEntityPiston var4 = this.func_176422_e(worldIn, pos);
      if(var4 == null) {
         return null;
      } else {
         float var5 = var4.func_145860_a(0.0F);
         // Kept adjusting the first parameter as this was done for extending before as well
         var5 = 1.0F - var5;

      // Use actual push facing instead of facing instead of the if before (This way it for sure does the same for extending/retracting)
         return this.func_176424_a(worldIn, pos, var4.func_174927_b(), var5, var4.getPushFacing());
      }
   }

   // Removed methods that don't change
}
