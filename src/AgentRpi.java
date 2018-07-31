import skuad.agentu.*;
import skuad.agentu.util.*;

public class AgentRpi extends AgentUAdapter {

	public final static String physical_specif = ""; //aucun slot physique
	public final static String social_specif = ""; //aucun slot social

	public void start(AgentUDescriptor aud, int ctx) {
		System.out.println(aud.getName()+ " opérationnel");
	}

	public void stop(AgentUDescriptor aud, int ctx) {
		System.out.println(aud.getName()+" off");
	}

	public static void main(String[] args) {
		ServerAU serverAU = ServerAU.newServer(null);
		int agent_id = serverAU.createAgent(AgentRpi.class, "AgentRpi");
		new AgentLogger(serverAU, AgentLogger.LOG | AgentLogger.STATE).observeAgent(agent_id);
		
		serverAU.runAgent(agent_id);
	}
}
