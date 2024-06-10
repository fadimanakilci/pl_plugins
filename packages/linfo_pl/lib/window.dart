/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/window/domain/models/window_model.dart';
import 'package:linfo_pl/src/window/presentation/controllers/window_controller.dart';

export 'package:linfo_pl/src/window/domain/models/window_model.dart' show WindowModel;

final _window = WindowController();

@override
Stream<WindowModel?> screenEventStream() {
  return _window.screenEventStream();
}

@override
Future<bool> keepScreenOn() {
  return _window.keepScreenOn();
}

@override
Future<bool> discardScreenOn() {
  return _window.discardScreenOn();
}

@override
Future<bool> getScreenOn() {
  return _window.getScreenOn();
}