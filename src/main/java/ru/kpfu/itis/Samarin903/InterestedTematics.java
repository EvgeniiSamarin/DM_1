package ru.kpfu.itis.Samarin903;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.queries.groups.GroupField;

import java.lang.reflect.Field;
import java.util.*;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI;

public class InterestedTematics {

    private static VkApiClient vk;
    private static UserActor actor;
    private static final int USER_ID = 0;// Write your id
    private static final String ACCESS_TOKEN = ""; // Write your token

    public static void main(String[] args) throws ClientException, ApiException, InterruptedException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);

        actor = new UserActor(USER_ID, ACCESS_TOKEN);
        List<Integer> friends = vk.friends().get(actor).userId(USER_ID).execute().getItems();
        Map<Integer, Set<String>> interests = new HashMap<Integer, Set<String>>();
        Set<String> mySet = getInterestsFromGroups(getGroupsIdForUser(2138153));
        for(int friend : friends) {
            interests.put(friend, getInterestsFromGroups(getGroupsIdForUser(friend)));
            for(Map.Entry<Integer, Set<String>> entry : interests.entrySet()) {
                int cnt = 0;
                for(String interes : entry.getValue()) {
                    if(mySet.contains(interes)) {
                        cnt++;
                    }
                }
                //TODO Запись файла в csv

            }
        }


    }

    public void getMap(List<Integer> friends ) {


    }
    private static List<Integer> getGroupsIdForUser(Integer userId) throws ClientException, ApiException {
        return vk.groups().get(actor).userId(userId).execute().getItems();
    }


    private static Set<String> getInterestsFromGroups(List<Integer> groupsId) throws ClientException, ApiException, InterruptedException {
        Set<String> interests = new HashSet<String>();
        for (Integer x : groupsId) {
            Thread.sleep(334);
            String activity = vk.groups().getById(actor).groupId(String.valueOf(x)).fields(GroupField.ACTIVITY).execute().get(0).getActivity();
            if (activity==null || activity.equals("Закрытая группа")||activity.equals("Открытая группа")) continue;
            interests.add(activity);
        }
        return interests;
    }



}
