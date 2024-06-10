/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

/// Event object provided to registered headlessTask.
class HeadlessTask {
  /// The task identifier
  String taskId;

  /// Signals whether this headless-task has timeout out.
  bool timeout;

  /// Create a new HeadlessTask instance.
  /// Automatically instantitated and provided to your registered headless task.
  HeadlessTask(this.taskId, this.timeout);
}