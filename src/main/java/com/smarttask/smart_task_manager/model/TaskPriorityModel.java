//package com.smarttask.smart_task_manager.model;
//
//import com.smarttask.smart_task_manager.entity.Task;
//import com.smarttask.smart_task_manager.enums.Priority;
//import lombok.RequiredArgsConstructor;
//
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//
//@RequiredArgsConstructor
//public class TaskPriorityModel {
//    private final SavedModelBundle model;
//
//    public Priority predict(Task task) {
//        try (Tensor input = buildInputTensor(task)) {
//            Tensor output = model.session().runner()
//                    .feed("input", input)
//                    .fetch("output")
//                    .run()
//                    .get(0)
//                    .expect(Float.class);
//
//            return output.floatValue() > 0.7 ? Priority.HIGH : Priority.MEDIUM;
//        }
//    }
//
//    private Tensor buildInputTensor(Task task) {
//        // Features: [days_until_deadline, user_workload]
//        float[] features = {
//                ChronoUnit.DAYS.between(LocalDate.now(), task.getDueDate()),
//                task.getAssignedUser().getTasks().size()
//        };
//        return Tensor.create(features);
//    }
//}
