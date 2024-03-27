/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:logging/logging.dart';

class LoggingUtil {
  final logger = Logger('MethodChannelSensors');
  String _log = '';

  LoggingUtil(String log) {
    _log = log;
  }

  LoggingUtil.warning(String log) {
    _log = log;

    logger.warning(_log);
  }

  LoggingUtil.info(String log) {
    _log = log;

    logger.info(_log);
  }

  LoggingUtil.fine(String log) {
    _log = log;

    logger.fine(_log);
  }
}
