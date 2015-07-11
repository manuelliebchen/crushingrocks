package Client.GUI.States.Interfaces;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * 
 * Interface to controll layer based game state handling.
 */
public interface IGameStateManager extends IDraw, IUpdate{

	public GameState pop();
    public GameState peek();
    public void push(GameState state);
    public void switchCurrentState(GameState state);
    public void resetWith(GameState state);

}
