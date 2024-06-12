/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

import 'package:linfo_pl/src/window/domain/sources/orientation_state_protocol.dart';

import '../../business/services/window_orientation_platform_interface.dart';

class WindowOrientationController implements WindowOrientationPlatformInterface {
  static WindowOrientationController? _singleton;

  WindowOrientationController._();

  factory WindowOrientationController() => _singleton ??= WindowOrientationController._();

  static WindowOrientationPlatformInterface get _platform => WindowOrientationPlatformInterface.instance;

  @override
  Stream<OrientationStateProtocol> orientationEventStream() {
    return _platform.orientationEventStream();
  }

  @override
  Future<OrientationStateProtocol> getOrientationState() {
    return _platform.getOrientationState();
  }

}