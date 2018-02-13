package ether;

import io.left.rightmesh.util.EtherUtility;
import org.json.simple.JSONObject;
import java.math.BigInteger;

/**
 * The JSON wrapper
 */
public final class JSON {

    //private C'tor to prevent initialization
    private JSON() {
    }

    /**
     * Gets the OK response to client.
     *
     * @param resMethod   The response method.
     * @return            The byte array.
     */
    public static byte[] getOkResponse(String resMethod) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("resMethod", resMethod);

        return response.toJSONString().getBytes();
    }


    /**
     * Sends ERROR response to client.
     *
     * @param resMethod   The response method
     * @return            The byte array.
     */
    public static byte[] getErrorResponse(String resMethod, String message) {
        JSONObject response = new JSONObject();
        response.put("status", "error");
        response.put("resMethod", resMethod);
        response.put("message", message);

        return response.toJSONString().getBytes();
    }


    /**
     * Sends response to getAll request.
     *
     * @param superPeerToClientChannel The SuperPeer-->Client channel info.
     * @param clientToSuperPeerChannel The Client-->SuperPeer channel info.
     * @param etherBalance             The Ether balance of the Client.
     * @param tokenBalance             The Tokens balance of the Client.
     * @param nonce                    The nonce of the Client.
     * @return                         The byte array.
     */
    public static byte[] sendGetAllResponse(EtherUtility.PaymentChannel superPeerToClientChannel,
                                          EtherUtility.PaymentChannel clientToSuperPeerChannel,
                                          String etherBalance, String tokenBalance, BigInteger nonce) {

        JSONObject spToClient = new JSONObject();
        spToClient.put("sender", superPeerToClientChannel.senderAddress);
        spToClient.put("recv", superPeerToClientChannel.recvAddress);
        spToClient.put("initDeposit", superPeerToClientChannel.initDeposit.toString());
        spToClient.put("openBlockNum", superPeerToClientChannel.openBlockNum.toString());
        spToClient.put("recvBalance", superPeerToClientChannel.recvBalance.toString());
        spToClient.put("lastRecvBalanceMsgSig", superPeerToClientChannel.lastRecvBalanceMsgSig);

        JSONObject clientToSp = new JSONObject();
        if (clientToSuperPeerChannel != null) {
            clientToSp.put("sender", clientToSuperPeerChannel.senderAddress);
            clientToSp.put("recv", clientToSuperPeerChannel.recvAddress);
            clientToSp.put("initDeposit", clientToSuperPeerChannel.initDeposit.toString());
            clientToSp.put("openBlockNum", clientToSuperPeerChannel.openBlockNum.toString());
            clientToSp.put("recvBalance", clientToSuperPeerChannel.recvBalance.toString());
            clientToSp.put("lastRecvBalanceMsgSig", clientToSuperPeerChannel.lastRecvBalanceMsgSig);
        }

        JSONObject response = new JSONObject();
        response.put("method", EtherUtility.RES_GET_ALL);
        response.put("status", "ok");
        response.put("etherBalance", etherBalance);
        response.put("tokenBalance", tokenBalance);
        response.put("nonce", nonce.toString());
        response.put("spToClient", spToClient);
        response.put("clientToSp", clientToSuperPeerChannel == null ? "none" : clientToSp);

        return response.toJSONString().getBytes();
    }


    /**
     * Sends response to Open channel request.
     *
     * @param clientToSuperPeerChannel The Client-->SuperPeer channel info.
     * @param etherBalance             The Ether balance of the Client.
     * @param tokenBalance             The Tokens balance of the Client.
     * @param nonce                    The nonce of the Client.
     * @return                         The byte array.
     */
    public static byte[] sendOpenClientToSpResponse(EtherUtility.PaymentChannel clientToSuperPeerChannel,
                                                  String etherBalance, String tokenBalance, BigInteger nonce) {

        JSONObject clientToSp = new JSONObject();
        clientToSp.put("sender", clientToSuperPeerChannel.senderAddress);
        clientToSp.put("recv", clientToSuperPeerChannel.recvAddress);
        clientToSp.put("initDeposit", clientToSuperPeerChannel.initDeposit.toString());
        clientToSp.put("openBlockNum", clientToSuperPeerChannel.openBlockNum.toString());
        clientToSp.put("recvBalance", clientToSuperPeerChannel.recvBalance.toString());
        clientToSp.put("lastRecvBalanceMsgSig", clientToSuperPeerChannel.lastRecvBalanceMsgSig);

        JSONObject response = new JSONObject();
        response.put("method", EtherUtility.RES_OPEN_CLIENT_TO_SUPER_PEER);
        response.put("status", "ok");
        response.put("etherBalance", etherBalance);
        response.put("tokenBalance", tokenBalance);
        response.put("nonce", nonce.toString());
        response.put("clientToSp", clientToSp);

        return response.toJSONString().getBytes();
    }


    /**
     * Sends the Balance Message Signature,
     * @param newBalance        The new balance.
     * @param balanceMsgSig     The signature.
     * @return                  The byte array.
     */
    public static byte[] sendBalanceMsgSig(BigInteger newBalance, String balanceMsgSig) {
        JSONObject msg = new JSONObject();
        msg.put("method", EtherUtility.METHOD_BALANCE_MSG_SIG);
        msg.put("status", "ok");
        msg.put("newBalance", newBalance.toString());
        msg.put("balanceMsgSig", balanceMsgSig);

        return msg.toJSONString().getBytes();
    }
}