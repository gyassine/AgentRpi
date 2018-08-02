import skuad.agentu.*;
import skuad.agentu.util.*;
import skuad.uda.Device;
import skuad.util.time.Pulse;

public class AgentRpi extends AgentUAdapter {

	/* Variables & Cie */
	public final static String physical_specif = "LED :  LIGHT"; // slot physique un dispositif de type lumière, donc un actionneur acceptant les commandes on/off
	public final static String social_specif = ""; //aucun slot social
	Device myLED;
	boolean light_state;
	
	/* Méthodes & Cie*/
	public AgentRpi(){
		myLED=null;
		light_state=false;
	}
	
	//Initialisation de l'agent
	public void start(AgentUDescriptor aud, int ctx) {
		aud.log(aud.getName()+ " opérationnel");
		aud.loopPulse("Blink", 3000,  3000, true); //Définition du comportement temporel de l'agent
	}

	public void stop(AgentUDescriptor aud, int ctx) {
		aud.log(aud.getName()+" off");
	}

	public void behavior(AgentUDescriptor aud, Pulse pulse){ //exécute le comportement de l'agent suite à l'activation d'un pulse, pulse = le descripteur de la pulsation qui a initié cet appel		
		if(pulse.is("Blink")){
			aud.log("Blink -> " + light_state);
			light_state = !light_state;
			//myLED.action(0, (light_state ? 1 : 0));
		}
	}
	//]

	//[Capture des changements de plugs
	public void notifyPlugDevice(AgentUDescriptor aud, String slot, Device device){
		aud.log("nouveau plug sur le slot "+slot);
		myLED=device;		
	}
	public void notifyUnplugDevice(AgentUDescriptor aud, String slot){
		aud.log("suppresion du plug sur le slot "+slot);
		myLED=null;		
	}
	public void notifyChangeDevice(AgentUDescriptor aud, String slot, Device device){
		aud.log("changement de plug sur le slot "+slot);
		myLED=device;		
	}	
	//]
	
	//[Programme principal
	public static void main(String[] args) {
		// Init
		ServerAU serverAU = ServerAU.newServer(null);
		int agent_id = serverAU.createAgent(AgentRpi.class, "AgentRpi");
		new AgentLogger(serverAU, AgentLogger.LOG | AgentLogger.STATE).observeAgent(agent_id);
		
		// Execution de l'agent
		serverAU.runAgent(agent_id);
		
		//Plug automatique sur un device de typ LIGHT présent sur le réseau
		serverAU.addPhysicalPlugRule(agent_id, "lumiere", "{ device : [ TYPE , LIGHT ] }");
	}
	//]
	
}
