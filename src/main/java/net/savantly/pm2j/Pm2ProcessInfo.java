package net.savantly.pm2j;

public class Pm2ProcessInfo {

	private String name;
	private int pm_id;
	private Pm2EnvInfo pm2_env;
	private Pm2MonitInfo monit;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPm_id() {
		return pm_id;
	}
	public void setPm_id(int pm_id) {
		this.pm_id = pm_id;
	}
	public Pm2EnvInfo getPm2_env() {
		return pm2_env;
	}
	public void setPm2_env(Pm2EnvInfo pm2_env) {
		this.pm2_env = pm2_env;
	}
	public Pm2MonitInfo getMonit() {
		return monit;
	}
	public void setMonit(Pm2MonitInfo monit) {
		this.monit = monit;
	}
	
}
