package net.minecraft.tileentity;

public class TileEntityPiston extends TileEntity implements
		IUpdatePlayerListBox {
	private IBlockState field_174932_a;
	private EnumFacing field_174931_f;
	private boolean extending;
	private boolean shouldHeadBeRendered;
	private float progress;
	private float lastProgress;
	private List field_174933_k = Lists.newArrayList();
	private static final String __OBFID = "CL_00000369";

	// Removed some methods that don't change

	/**
	 * ADDED: Returns the actual direction the piston pushes in
	 * 
	 * @return facing to the direction the piston pushes in
	 */
	public EnumFacing getPushFacing() {
		if (this.extending) {
			return this.field_174931_f;
		} else {
			return this.field_174931_f.getOpposite();
		}
	}

	// Method that moves the entities
	private void func_145863_a(float p_145863_1_, float p_145863_2_) {
		// Kept adjusting the first parameter as this was done for extending
		// before as well
		p_145863_1_ = 1.0F - p_145863_1_;
		// Get actual push direction and replaced all calls of the facing in
		// this method with this facing
		// This way it will work exactly the same without having to mess with
		// all the single values
		EnumFacing pushFacing = getPushFacing();

		AxisAlignedBB var3 = Blocks.piston_extension.func_176424_a(
				this.worldObj, this.pos, this.field_174932_a, p_145863_1_,
				pushFacing);
		if (var3 != null) {
			List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(
					(Entity) null, var3);
			if (!var4.isEmpty()) {
				this.field_174933_k.addAll(var4);
				Iterator var5 = this.field_174933_k.iterator();

				while (var5.hasNext()) {
					Entity var6 = (Entity) var5.next();
					// REMOVED: "&& this.extending", to make slime blocks bounce
					// when the piston is retracting
					if (this.field_174932_a.getBlock() == Blocks.slime_block) {
						switch (TileEntityPiston.SwitchAxis.field_177248_a[pushFacing
								.getAxis().ordinal()]) {
						case 1:
							var6.motionX = (double) pushFacing
									.getFrontOffsetX();
							break;
						case 2:
							var6.motionY = (double) pushFacing
									.getFrontOffsetY();
							break;
						case 3:
							var6.motionZ = (double) pushFacing
									.getFrontOffsetZ();
						}
					}
					// Made it move the entity also when it's a slime block. See
					// MC-??? for additional information about that.
					var6.moveEntity((double) (p_145863_2_ * (float) pushFacing
							.getFrontOffsetX()),
							(double) (p_145863_2_ * (float) pushFacing
									.getFrontOffsetY()),
							(double) (p_145863_2_ * (float) pushFacing
									.getFrontOffsetZ()));
				}

				this.field_174933_k.clear();
			}
		}
	}

	// Removed some methods that don't change

	public void update() {
		this.lastProgress = this.progress;
		if (this.lastProgress >= 1.0F) {
			this.func_145863_a(1.0F, 0.25F);
			this.worldObj.removeTileEntity(this.pos);
			this.invalidate();
			if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
				this.worldObj.setBlockState(this.pos, this.field_174932_a, 3);
				this.worldObj.notifyBlockOfStateChange(this.pos,
						this.field_174932_a.getBlock());
			}
		} else {
			this.progress += 0.5F;
			if (this.progress >= 1.0F) {
				this.progress = 1.0F;
			}

			// Call the movement method always, no matter if extending or
			// retracting.
			this.func_145863_a(this.progress, this.progress - this.lastProgress
					+ 0.0625F);
		}
	}

	// Removed some methods that don't change
}
