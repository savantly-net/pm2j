package net.savantly.pm2j;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class Pm2EnvInfo {
	private String name;
	private int instances;
	private boolean vizion;
	private boolean pmx;
	private boolean automation;
	private boolean autorestart;
	private boolean treekill;
	private Map<String, String> env = new HashMap<>();
	private Pm2ProcessMode exec_mode;
	private String[] node_args;
	private Pm2ProcessStatus status;
	private long pm_uptime;
	private DateTime created_at;
	private int restart_time;
	private int unstable_restarts;
	private String _pm2_version;
	private String node_version;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getInstances() {
		return instances;
	}
	public void setInstances(int instances) {
		this.instances = instances;
	}
	public boolean isVizion() {
		return vizion;
	}
	public void setVizion(boolean vizion) {
		this.vizion = vizion;
	}
	public boolean isPmx() {
		return pmx;
	}
	public void setPmx(boolean pmx) {
		this.pmx = pmx;
	}
	public boolean isAutomation() {
		return automation;
	}
	public void setAutomation(boolean automation) {
		this.automation = automation;
	}
	public boolean isAutorestart() {
		return autorestart;
	}
	public void setAutorestart(boolean autorestart) {
		this.autorestart = autorestart;
	}
	public boolean isTreekill() {
		return treekill;
	}
	public void setTreekill(boolean treekill) {
		this.treekill = treekill;
	}
	public Map<String, String> getEnv() {
		return env;
	}
	public void setEnv(Map<String, String> env) {
		this.env = env;
	}
	public Pm2ProcessMode getExec_mode() {
		return exec_mode;
	}
	public void setExec_mode(Pm2ProcessMode exec_mode) {
		this.exec_mode = exec_mode;
	}
	public String[] getNode_args() {
		return node_args;
	}
	public void setNode_args(String[] node_args) {
		this.node_args = node_args;
	}
	public Pm2ProcessStatus getStatus() {
		return status;
	}
	public void setStatus(Pm2ProcessStatus status) {
		this.status = status;
	}
	public long getPm_uptime() {
		return pm_uptime;
	}
	public void setPm_uptime(long pm_uptime) {
		this.pm_uptime = pm_uptime;
	}
	public DateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(DateTime created_at) {
		this.created_at = created_at;
	}
	public int getRestart_time() {
		return restart_time;
	}
	public void setRestart_time(int restart_time) {
		this.restart_time = restart_time;
	}
	public int getUnstable_restarts() {
		return unstable_restarts;
	}
	public void setUnstable_restarts(int unstable_restarts) {
		this.unstable_restarts = unstable_restarts;
	}
	public String get_pm2_version() {
		return _pm2_version;
	}
	public void set_pm2_version(String _pm2_version) {
		this._pm2_version = _pm2_version;
	}
	public String getNode_version() {
		return node_version;
	}
	public void setNode_version(String node_version) {
		this.node_version = node_version;
	}
	
}
