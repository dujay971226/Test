import java.util.Collections;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.objects_api.channel.PNChannelMetadataResult;
import com.pubnub.api.models.consumer.objects_api.membership.PNMembershipResult;
import com.pubnub.api.models.consumer.objects_api.uuid.PNUUIDMetadataResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.files.PNFileEventResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;
import org.jetbrains.annotations.NotNull;


public class App {
    public static void main(String[] args) throws PubNubException {
        final UserId userId = new UserId("Jay");
        PNConfiguration pnConfiguration = new PNConfiguration(userId);
        pnConfiguration.setSubscribeKey("sub-c-85105a9c-650a-4f5b-857c-7454a8ce3f7e");
        pnConfiguration.setPublishKey("pub-c-6db69944-58c5-4ce7-aaef-63ad2e37e886");
        pnConfiguration.setSecretKey("sec-c-OWRhYWUxZjAtN2U3My00ZjEwLWExMzUtYzYxNWI1Mjc0OGYw");
        PubNub pubnub = new PubNub(pnConfiguration);

        final String channelName = "myChannel";

        // create message payload using Gson
        final JsonObject messageJsonObject1 = new JsonObject();
        messageJsonObject1.addProperty("msg", "Hello World No.1");
        final JsonObject messageJsonObject2 = new JsonObject();
        messageJsonObject2.addProperty("msg", "Hello World No.2");

        pubnub.subscribe()
                .channels(Collections.singletonList(channelName))
                .execute();
        pubnub.addListener(new SubscribeCallback() {

            @Override
            public void status(PubNub pubnub, PNStatus status) {
                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    System.out.println("disconnected 1");
                } else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                    System.out.println("connected 1");

                } else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                    System.out.println("reconnected 1");// Happens as part of our regular operation. This event happens when
                    // radio / connectivity is lost, then regained.
                } else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {
                    System.out.println("decryption error 1");
                    // Handle message decryption error. Probably client configured to
                    // encrypt messages and on live data feed it received plain text.
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // Handle new message stored in message.message
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()
                } else {
                    // Message has been received on channel stored in
                    // message.getSubscription()
                }

                JsonElement receivedMessageObject = message.getMessage();
                System.out.println("Received message: " + receivedMessageObject.toString());
                // extract desired parts of the payload, using Gson
                String msg = message.getMessage().getAsJsonObject().get("msg").getAsString();
                System.out.println("The content of the message is: " + msg);

                /*
                 * Log the following items with your favorite logger - message.getMessage() -
                 * message.getSubscription() - message.getTimetoken()
                 */
            }

            @Override
            public void signal(PubNub pubnub, PNSignalResult pnSignalResult) {

            }

            @Override
            public void uuid(PubNub pubnub, PNUUIDMetadataResult pnUUIDMetadataResult) {

            }

            @Override
            public void channel(@NotNull PubNub pubNub, @NotNull PNChannelMetadataResult pnChannelMetadataResult) {

            }

            @Override
            public void membership(PubNub pubnub, PNMembershipResult pnMembershipResult) {

            }

            @Override
            public void messageAction(PubNub pubnub, PNMessageActionResult pnMessageActionResult) {

            }

            @Override
            public void file(@NotNull PubNub pubNub, @NotNull PNFileEventResult pnFileEventResult) {

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });



        pubnub.publish()
                .channel(channelName)
                .message(messageJsonObject1)
                .async((result, publishStatus) -> {
                    if (!publishStatus.isError()) {
                        System.out.println("Sent 1st message success");
                    }
                    // Request processing failed.
                    else {
                        System.out.println("Sent 1st message failed");
                        // Handle message publish error.
                        // Check 'category' property to find out
                        // issues because of which the request failed.
                        // Request can be resent using: [status retry];
                    }
                });

        pubnub.unsubscribe().channels(Collections.singletonList(channelName))
                .execute();

        pubnub.subscribe()
                .channels(Collections.singletonList(channelName))
                .execute();

        pubnub.addListener(new SubscribeCallback() {

            @Override
            public void status(PubNub pubnub, PNStatus status) {
                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    System.out.println("disconnected 2");
                } else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                    System.out.println("connected 2");

                } else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                    System.out.println("reconnected 2");// Happens as part of our regular operation. This event happens when
                    // radio / connectivity is lost, then regained.
                } else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {
                    System.out.println("decryption error 2");
                    // Handle message decryption error. Probably client configured to
                    // encrypt messages and on live data feed it received plain text.
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // Handle new message stored in message.message
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()
                } else {
                    // Message has been received on channel stored in
                    // message.getSubscription()
                }

                JsonElement receivedMessageObject = message.getMessage();
                System.out.println("Received message: " + receivedMessageObject.toString());
                // extract desired parts of the payload, using Gson
                String msg = message.getMessage().getAsJsonObject().get("msg").getAsString();
                System.out.println("The content of the message is: " + msg);

                /*
                 * Log the following items with your favorite logger - message.getMessage() -
                 * message.getSubscription() - message.getTimetoken()
                 */
            }

            @Override
            public void signal(PubNub pubnub, PNSignalResult pnSignalResult) {

            }

            @Override
            public void uuid(PubNub pubnub, PNUUIDMetadataResult pnUUIDMetadataResult) {

            }

            @Override
            public void channel(@NotNull PubNub pubNub, @NotNull PNChannelMetadataResult pnChannelMetadataResult) {

            }

            @Override
            public void membership(PubNub pubnub, PNMembershipResult pnMembershipResult) {

            }

            @Override
            public void messageAction(PubNub pubnub, PNMessageActionResult pnMessageActionResult) {

            }

            @Override
            public void file(@NotNull PubNub pubNub, @NotNull PNFileEventResult pnFileEventResult) {

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });


        pubnub.publish()
                .channel(channelName)
                .message(messageJsonObject2)
                .async((result, publishStatus) -> {
                    if (!publishStatus.isError()) {
                        System.out.println("Sent 2nd message success");
                    }
                    // Request processing failed.
                    else {
                        System.out.println("Sent 2nd message failed");
                        // Handle message publish error.
                        // Check 'category' property to find out
                        // issues because of which the request failed.
                        // Request can be resent using: [status retry];
                    }
                });

    }
}