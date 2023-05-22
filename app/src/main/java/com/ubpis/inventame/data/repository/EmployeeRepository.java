package com.ubpis.inventame.data.repository;


import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ubpis.inventame.data.model.Employee;
import com.ubpis.inventame.data.model.UserType;

import java.util.ArrayList;
import java.util.Map;

public class EmployeeRepository {
    private static final String TAG = "EmployeeRepository";

    private static final EmployeeRepository instance = new EmployeeRepository();

    private final CollectionReference employeesCollection;
    public ArrayList<OnLoadEmployeesListener> onLoadEmployeesListeners = new ArrayList<>();

    public ArrayList<OnAddEmployeeListener> onAddEmployeeListeners = new ArrayList<>();
    private EmployeeRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build());
        this.employeesCollection = db.collection("users");
    }

    public static EmployeeRepository getInstance() {
        return instance;
    }

    public void addOnLoadEmployeesListener(OnLoadEmployeesListener listener) {
        onLoadEmployeesListeners.add(listener);
    }

    public void addOnAddEmployeeListener(OnAddEmployeeListener listener) {
        onAddEmployeeListeners.add(listener);
    }

    public void getEmployees(ArrayList<Employee> employees, String businessId) {
        Query query = employeesCollection.whereEqualTo("type", UserType.EMPLOYEE.toString())
                .whereEqualTo("businessId", businessId).orderBy("createdAt", Query.Direction.DESCENDING);
        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null || !querySnapshot.isEmpty()) {
                            employees.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Employee employee = new Employee();
                                employee.setId(document.getId());
                                employee.setName(document.getString("name"));
                                employee.setLastname(document.getString("lastname"));
                                employee.setEmail(document.getString("email"));
                                employee.setDocumentNumber(document.getString("documentNumber"));
                                employee.setImageUrl(document.getString("imageUrl"));
                                employee.setCreatedAt(document.getTimestamp("createdAt"));
                                employee.setDeletedAt(document.getTimestamp("deletedAt"));
                                employee.setBusinessId(document.getString("businessId"));
                                employee.setType(UserType.valueOf(document.getString("type")));
                                employees.add(employee);
                            }
                            for (OnLoadEmployeesListener l : onLoadEmployeesListeners) {
                                l.onLoadEmployees(employees, querySnapshot.getMetadata().isFromCache());
                            }
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void addEmployee(Employee employee) {
        employeesCollection.document(employee.getId()).set(employee).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + task.getResult());
                for (OnAddEmployeeListener l : onAddEmployeeListeners) {
                    l.onAddEmployee(employee);
                }
            } else {
                Log.w(TAG, "Error adding document", task.getException());
            }
        });
    }

    public void updateEmployee(Employee employee) {
        Map<String, Object> map = employee.toMap();
        employeesCollection.document(employee.getId()).update(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + task.getResult());
            } else {
                Log.w(TAG, "Error adding document", task.getException());
            }
        });
    }

    public void deleteEmployee(String id) {
        employeesCollection.document(id).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + task.getResult());
            } else {
                Log.w(TAG, "Error adding document", task.getException());
            }
        });
    }

    public interface OnLoadEmployeesListener {
        void onLoadEmployees(ArrayList<Employee> employees, boolean isFromCache);
    }

    public interface OnAddEmployeeListener {
        void onAddEmployee(Employee employee);
    }

}