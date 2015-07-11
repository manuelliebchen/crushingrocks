package Client.GUI.States.Interfaces;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * 
 * Interface to controll layer based game state handling.
 */
public interface IGameStateManager extends IDraw, IUpdate{

	public IGameState pop();
    public IGameState peek();
    public void push(IGameState state);
    public void switchCurrentState(IGameState state);
    public void resetWith(IGameState state);

}
