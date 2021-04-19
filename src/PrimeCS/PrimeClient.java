package PrimeCS;

import java.io.Serializable;
import java.util.function.Consumer;

public class PrimeClient extends NetworkConnection {
	
	private String ip;
	private int port;

	public PrimeClient(String ip, int port, Consumer<Serializable> oneReceiveCallBack) {
		super(oneReceiveCallBack);
		this.ip = ip;
		this.port = port;
	}

	@Override
	protected boolean isServer() {
		return false;
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
