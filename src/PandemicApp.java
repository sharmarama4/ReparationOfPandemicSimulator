import java.util.*;

public class PandemicApp {
    public static void main(String[] args) {
        List<Patient> patientList = new ArrayList<>();
        patientList.addAll(Patient.getAllPatients());

        Set<Patient> patientSet = new LinkedHashSet<>();
        patientSet.addAll(patientList);
        System.out.println("-----Opdracht 1");
        for (Patient patient:patientSet){
            System.out.println(patient);
        }

        System.out.println("-----Opdracht 2");

        Set<Patient> patientTreeSet = new TreeSet<>(new TemperatureSorter().thenComparing(new AgeSorter()));
        patientTreeSet.addAll(patientSet);

        patientTreeSet.add(new Patient("030801321", "Gringoire Lune", 33, 35, true, true));
//        for (Patient patient: patientTreeSet){
//            System.out.println(patient);
//        }

        List<Patient> sortedPatientList = new ArrayList<>();
        sortedPatientList.addAll(patientSet);
        sortedPatientList.sort(new TemperatureSorter().reversed()
                .thenComparing(new AgeSorter().reversed())
                .thenComparing(new InsuranceSorter().reversed()));

        for (Patient patient : sortedPatientList){
            System.out.println(patient);
        }

        System.out.println("-----Opdracht 3");

        Queue<Patient> patientQueue = new LinkedList<>();
        patientQueue.addAll(sortedPatientList);

        Map<Integer, List<Patient>> categoryMap = new LinkedHashMap<>();

        categoryMap.put(1, new ArrayList<>());
        categoryMap.put(2, new ArrayList<>());
        categoryMap.put(3, new ArrayList<>());
        categoryMap.put(4, new ArrayList<>());

        while (patientQueue.size()>0){

            Patient patient = patientQueue.poll();
            List<Patient> patients;

            if (((patient.getAge()>=65 && patient.getTemperature()>=38) || patient.getTemperature()>=40) && patient.isUnKnownVirus()){
                patients = categoryMap.get(1);
                patients.add(patient);
                categoryMap.put(1,patients);
            }else if(patient.getTemperature()>=38 && patient.isUnKnownVirus()){
                patients = categoryMap.get(2);
                patients.add(patient);
                categoryMap.put(2,patients);
            }else if(patient.isUnKnownVirus()){
                patients = categoryMap.get(3);
                patients.add(patient);
                categoryMap.put(3,patients);
            }else if (patient.getTemperature()>=38){
                patients = categoryMap.get(4);
                patients.add(patient);
                categoryMap.put(4,patients);
            }
        }

        for(Map.Entry<Integer, List<Patient>> category : categoryMap.entrySet()){
            int key = category.getKey();

            System.out.println("For category "+key+ " we have following patients:");
            for (Patient patient: categoryMap.get(key)){
                System.out.println(patient);
            }

            System.out.println("----Next category!---");
        }






    }
}

