package Client.GUI.States.Interfaces;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 */
public interface IGameState {

	public void entered();
    public void leaving();
    public void obscuring();
    public void revealed();
    
}
