package net.savantly.pm2j;

public class Pm2MonitInfo {
	private long memory;
	private int cpu;
	
	public long getMemory() {
		return memory;
	}
	public void setMemory(long memory) {
		this.memory = memory;
	}
	public int getCpu() {
		return cpu;
	}
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
}
