/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../../business/services/window_platform_interface.dart';
import '../../domain/models/window_model.dart';

class WindowController implements WindowPlatformInterface {
  static WindowController? _singleton;

  WindowController._();

  factory WindowController() => _singleton ??= WindowController._();

  static WindowPlatformInterface get _platform => WindowPlatformInterface.instance;

  @override
  Stream<WindowModel?> screenEventStream() {
    return _platform.screenEventStream();
  }

  @override
  Future<bool> keepScreenOn() {
    return _platform.keepScreenOn();
  }

  @override
  Future<bool> discardScreenOn() {
    return _platform.discardScreenOn();
  }

  @override
  Future<bool> getScreenOn() {
    return _platform.getScreenOn();
  }
}