package PrimeCS;

import java.io.Serializable;
import java.util.function.Consumer;

public class PrimeServer extends NetworkConnection {
	
	private int port;

	public PrimeServer(int port, Consumer<Serializable> oneReceiveCallBack) {
		super(oneReceiveCallBack);
		this.port = port;
	
	}

	@Override
	protected boolean isServer() {
		return true;
	}

	@Override
	protected String getIP() {
		
		return null;
	}

	@Override
	protected int getPort() {
		return port;
	}

}
