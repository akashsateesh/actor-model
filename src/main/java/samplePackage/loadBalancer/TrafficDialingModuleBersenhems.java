package samplePackage.loadBalancer;



import java.util.concurrent.atomic.AtomicReference;

public class TrafficDialingModuleBersenhems {


    private volatile int[] pair;
    private volatile double rpasWeight;

    public TrafficDialingModuleBersenhems(){
        pair = new int[]{0,0};
        rpasWeight = 0.0;
    }

    public synchronized Service calculate(double weight){

        if(weight != rpasWeight){
            rpasWeight = weight;
            pair[0] = pair[1] = 0;
        }

        double weightRatio = 100.0;

        Service service = Service.IDM;

        if(Double.compare(rpasWeight,0.0) > 0)
            weightRatio = (1 - rpasWeight)/(rpasWeight);

        weightRatio = Math.atan(weightRatio);

        double idmDelta = pair[0] == 0 ? 100.0 : ((double) pair[1] + 1.0) / (double) pair[0];
        double rpasDeleta = (double) pair[1] / ((double) pair[0] + 1.0);

        if (Math.abs(weightRatio - Math.atan(idmDelta)) < Math.abs(weightRatio - Math.atan(rpasDeleta))) {
            pair[1] += 1;
        } else {
            pair[0] += 1;
            service = Service.RPAS;
        }

        if (pair[0] + pair[1] == 100)
            pair[0] = pair[1] = 0;

        return service;
    }

}
