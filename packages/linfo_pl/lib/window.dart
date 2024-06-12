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
import 'package:linfo_pl/src/window/domain/sources/keyguard_status_protocol.dart';
import 'package:linfo_pl/src/window/domain/sources/orientation_state_protocol.dart';
import 'package:linfo_pl/src/window/presentation/controllers/window_controller.dart';
import 'package:linfo_pl/src/window/presentation/controllers/window_orientation_controller.dart';

export 'package:linfo_pl/src/window/domain/models/window_model.dart' show WindowModel;
export 'package:linfo_pl/src/window/domain/sources/orientation_state_protocol.dart' show OrientationStateProtocol;
export 'package:linfo_pl/src/window/domain/sources/keyguard_status_protocol.dart' show KeyguardStatusProtocol;

final _window = WindowController();
final _windowOrientation = WindowOrientationController();

@override
Stream<WindowModel?> screenEventStream() {
  return _window.screenEventStream();
}

@override
Stream<OrientationStateProtocol> orientationEventStream() {
  return _windowOrientation.orientationEventStream();
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

@override
Future<KeyguardStatusProtocol> requestKeyguard() {
  return _window.requestKeyguard();
}

@override
Future<bool> changeBrightness({double? brightness}) {
  return _window.changeBrightness(brightness: brightness);
}

@override
Future<OrientationStateProtocol> getOrientationState() {
  return _windowOrientation.getOrientationState();
}