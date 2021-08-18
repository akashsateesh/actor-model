package samplePackage.loadBalancer;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucket {

    private final Map<String,ConcurrentHashMap<String,Long>> lastRecordedTime = new ConcurrentHashMap<>();

    private final Map<String,ConcurrentHashMap<String,Integer>> numofRequestsAvailable = new ConcurrentHashMap<>();

    private final Object ob = new Object();

    public Integer count = 0;

    public boolean isLeakyActive(String apiName, String offeringId, Long durationInMillis, Integer samplingRate){
        boolean isRoute = false;

        if(!lastRecordedTime.containsKey(apiName)){
            lastRecordedTime.putIfAbsent(apiName,new ConcurrentHashMap<>());
        }

        if(!numofRequestsAvailable.containsKey(apiName)){
            numofRequestsAvailable.putIfAbsent(apiName,new ConcurrentHashMap<>());
        }

        ConcurrentHashMap<String,Long> offeringIdLastTimeMap = lastRecordedTime.get(apiName);
        ConcurrentHashMap<String,Integer> numberOfRequestsAvailable = numofRequestsAvailable.get(apiName);

        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - offeringIdLastTimeMap.getOrDefault(offeringId,currentTime);

        if(!offeringIdLastTimeMap.containsKey(offeringId) || numberOfRequestsAvailable.get(offeringId) > samplingRate || timeElapsed > durationInMillis){
            offeringIdLastTimeMap.put(offeringId,currentTime);
            numberOfRequestsAvailable.put(offeringId,0);
        }

        if(timeElapsed <= durationInMillis && numberOfRequestsAvailable.get(offeringId) < samplingRate){
            numberOfRequestsAvailable.computeIfPresent(offeringId, (k,v) -> v + 1);
            isRoute = true;
        }

        return isRoute;
    }

    public boolean isActive(String apiName, String offeringId, Long durationInMillis, Integer samplingRate) {

        boolean isRoute = false;

        if(!lastRecordedTime.containsKey(apiName)){
            lastRecordedTime.putIfAbsent(apiName,new ConcurrentHashMap<>());
        }

        if(!numofRequestsAvailable.containsKey(apiName)){
            numofRequestsAvailable.putIfAbsent(apiName,new ConcurrentHashMap<>());
        }

        ConcurrentHashMap<String,Long> offeringIdLastTimeMap = lastRecordedTime.get(apiName);
        ConcurrentHashMap<String,Integer> numberOfRequestsAvailable = numofRequestsAvailable.get(apiName);

        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - offeringIdLastTimeMap.getOrDefault(offeringId,currentTime);

        // All Tokens have expired or no call is made
        if(!offeringIdLastTimeMap.containsKey(offeringId) || timeElapsed > durationInMillis){
            offeringIdLastTimeMap.put(offeringId,currentTime);
            numberOfRequestsAvailable.put(offeringId,samplingRate);
        }

        synchronized (offeringId){

            numberOfRequestsAvailable.computeIfPresent(offeringId, (k,v) -> v-1);

            System.out.println(timeElapsed+" "+numberOfRequestsAvailable.get(offeringId));

            return numberOfRequestsAvailable.get(offeringId) >= 0;
        }
    }

}
