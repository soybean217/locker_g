package com.highguard.Wisdom.struts.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CmdSocketIO {
	public static int REMOTE_EXEC_SUCCESS = 0x0000;
	public static int REMOTE_EXEC_FAIL = 0x0001;

	Socket socket = null;
	InputStream iStream = null;
	OutputStream oStream = null;
	DataInputStream in = null;
	DataOutputStream out = null;

	public CmdSocketIO() {

	}

	public boolean connect(String host, int port) throws Exception {
		try {
			// create a socket
			this.socket = new Socket(host, port);
			this.socket.setSoLinger(true, 0);

			// set up input stream
			this.iStream = socket.getInputStream();
			this.in = new DataInputStream(this.iStream);

			// set up output stream
			this.oStream = socket.getOutputStream();
			this.out = new DataOutputStream(this.oStream);

			return true;
		} catch (UnknownHostException e) {
			this.close();
			throw e;
		} catch (IOException e) {
			this.close();
			throw e;
		} catch (SecurityException e) {
			this.close();
			throw e;
		} catch (Exception e) {
			this.close();
			throw e;
		}

	}

	public int read() {
		int readTimeout = 1000; // 1 sec
		int retry = 10;
		int len = 0;
		int response = REMOTE_EXEC_FAIL;
		int i = 0;

		try {
			if (this.in != null) {

				// set timeout for blocking read
				socket.setSoTimeout(readTimeout);

				while (len <= 0 && i < retry) {
					Thread.sleep(readTimeout); // wait 1 sec

					// is there data
					len = in.available();

					i++;
				}

				if (i == retry) {
					this.close();
				} else {
					response = in.readInt();

					// debug
					// System.out.println("SocketIO.read: resp = " +
					// Integer.toHexString(response));

				}
			}
		} catch (IOException e) {

			this.close();
		} catch (Exception e) {

			this.close();
		}

		return response;

	}

	public String write(String cmdname) {
		String s = cmdname;

		/*
		 * // Arrange in network byte order (big endian) eg. 0x4001 => 0x00 0x00
		 * 0x40 0x01 // The consumer is NML - a C program expecting an unsigned
		 * int in network byte order. // So we send the high order bytes first.
		 */
		int commandlen = 0;
		commandlen = cmdname.getBytes().length;
		byte[] b = new byte[4];
		b[0] = (byte) ((commandlen >>> 24) & 0xFF);
		b[1] = (byte) ((commandlen >>> 16) & 0xFF);
		b[2] = (byte) ((commandlen >>> 8) & 0xFF);
		b[3] = (byte) (commandlen & 0xFF);

		try {
			/* write out byte array */

			this.out.write(b, 0, b.length);
			this.out.write(cmdname.getBytes(), 0, commandlen);
		} catch (IOException e) {

			this.close();
		} catch (Exception e) {
			this.close();
		}

		return s;
	}

	/**
	 * write the buffer into the socker with the specified parameters
	 * 
	 * @param cmdType
	 *            the type fo the command Type = 0 ,1, 2
	 * @param cmdName
	 *            the name of the command
	 * @param parameters
	 *            the parameters to execute the command
	 * @return the command string
	 */

	public String write(int cmdType, String cmdName, String cmdParameters) {

		String s = cmdName;
		if (cmdName == null || cmdName.length() == 0) {
			return s;
		}
		/*
		 * // Arrange in network byte order (big endian) eg. 0x4001 => 0x00 0x00
		 * 0x40 0x01 // The consumer is NML - a C program expecting an unsigned
		 * int in network byte order. // So we send the high order bytes first.
		 */

		if (cmdName == null || cmdName.length() == 0) {
			return s;
		}
		byte[] bCmdType = new byte[4];
		bCmdType[0] = (byte) ((cmdType >>> 24) & 0xFF);
		bCmdType[1] = (byte) ((cmdType >>> 16) & 0xFF);
		bCmdType[2] = (byte) ((cmdType >>> 8) & 0xFF);
		bCmdType[3] = (byte) (cmdType & 0xFF);

		int cmdNameLength = 0;
		cmdNameLength = cmdName.getBytes().length;
		byte[] bCmdNameLength = new byte[4];
		bCmdNameLength[0] = (byte) ((cmdNameLength >>> 24) & 0xFF);
		bCmdNameLength[1] = (byte) ((cmdNameLength >>> 16) & 0xFF);
		bCmdNameLength[2] = (byte) ((cmdNameLength >>> 8) & 0xFF);
		bCmdNameLength[3] = (byte) (cmdNameLength & 0xFF);

		try {
			/* write out byte array */
			this.out.write(bCmdType, 0, bCmdType.length);
			this.out.write(bCmdNameLength, 0, bCmdNameLength.length);
			this.out.write(cmdName.getBytes(), 0, cmdNameLength);
			if (cmdType == 1) {
				int cmdParametersLength = 0;
				byte[] bCmdParametersLength = new byte[4];
				if (cmdParameters != null && cmdParameters.length() > 0) {
					cmdParametersLength = cmdParameters.getBytes().length;
				}
				bCmdParametersLength[0] = (byte) ((cmdParametersLength >>> 24) & 0xFF);
				bCmdParametersLength[1] = (byte) ((cmdParametersLength >>> 16) & 0xFF);
				bCmdParametersLength[2] = (byte) ((cmdParametersLength >>> 8) & 0xFF);
				bCmdParametersLength[3] = (byte) (cmdParametersLength & 0xFF);
				this.out.write(bCmdParametersLength, 0,
						bCmdParametersLength.length);
				if (cmdParametersLength > 0) {
					this.out.write(cmdParameters.getBytes(), 0,
							cmdParametersLength);
				}
			}
		} catch (IOException e) {
			this.close();
		} catch (Exception e) {
			this.close();
		}
		return s;
	}

	public boolean close() {
		try {
			if (this.iStream != null) {
				this.iStream.close();
				this.iStream = null;
			}

			if (this.oStream != null) {
				this.oStream.close();
				this.oStream = null;
			}

			if (this.socket != null) {
				this.socket.close();
				this.socket = null;
			}

			this.in = null;
			this.out = null;

			return true;
		}  catch (Exception e) {
		}

		return false;
	}

} // SocketIO

