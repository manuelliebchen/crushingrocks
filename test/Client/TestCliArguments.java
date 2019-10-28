package Client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.beust.jcommander.ParameterException;

import Client.CliArguments;

public class TestCliArguments {

	@Test
	public void testCliArguments() {
		CliArguments cli = new CliArguments();
		
		assertFalse(cli.isDebug());
		assertTrue(cli.showGui());
		assertEquals(0, cli.numberOfGames());
		assertEquals("", cli.selectedGameMode());
		assertEquals(new ArrayList<>(), cli.selectedBots());
	}
	
	@Test
	public void testCliArgumentsDebug() {
		CliArguments cli = new CliArguments("--debug");
		
		assertTrue(cli.isDebug());
		assertTrue(cli.showGui());
		assertEquals(0, cli.numberOfGames());
		assertEquals("", cli.selectedGameMode());
		assertEquals(new ArrayList<>(), cli.selectedBots());
	}
	
	@Test
	public void testCliArgumentsNoGui() {
		CliArguments cli = new CliArguments("--nogui");
		
		assertFalse(cli.isDebug());
		assertFalse(cli.showGui());
		assertEquals(0, cli.numberOfGames());
		assertEquals("", cli.selectedGameMode());
		assertEquals(new ArrayList<>(), cli.selectedBots());
	}
	
	@Test
	public void testCliArgumentsNumberOfGames() {
		CliArguments cli = new CliArguments("--count 8".split(" "));
		
		assertFalse(cli.isDebug());
		assertTrue(cli.showGui());
		assertEquals(8, cli.numberOfGames());
		assertEquals("", cli.selectedGameMode());
		assertEquals(new ArrayList<>(), cli.selectedBots());
	}
	
	@Test
	public void testCliArgumentsGameMode() {
		CliArguments cli = new CliArguments("--mode Test".split(" "));
		
		assertFalse(cli.isDebug());
		assertTrue(cli.showGui());
		assertEquals(0, cli.numberOfGames());
		assertEquals("Test", cli.selectedGameMode());
		assertEquals(new ArrayList<>(), cli.selectedBots());
	}
	
	@Test
	public void testCliArgumentsBots() {
		CliArguments cli = new CliArguments("--bot Hallo.class --bot Welt.class".split(" "));
		
		assertFalse(cli.isDebug());
		assertTrue(cli.showGui());
		assertEquals(0, cli.numberOfGames());
		assertEquals("", cli.selectedGameMode());
		assertEquals(2, cli.selectedBots().size());
		assertEquals("Hallo.class", cli.selectedBots().get(0));
		assertEquals("Welt.class", cli.selectedBots().get(1));
	}
	
	@Test(expected = ParameterException.class)
	public void testCliArgumentsUnknwonParam() {
		new CliArguments("--test");
	}
	
	@Test(expected = ParameterException.class)
	public void testCliArgumentsWrongNumber() {
		new CliArguments("--count acht".split(" "));
	}
}
