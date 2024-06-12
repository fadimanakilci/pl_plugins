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
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../controllers/window_orientation_channel_controller.dart';

interface class WindowOrientationPlatformInterface extends PlatformInterface {
  WindowOrientationPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static WindowOrientationPlatformInterface _instance = WindowOrientationChannelController();

  static WindowOrientationPlatformInterface get instance => _instance;

  static set instance(WindowOrientationPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Stream<OrientationStateProtocol> orientationEventStream() {
    throw UnimplementedError(
        'orientationEventStream has not been implemented.');
  }

  Future<OrientationStateProtocol> getOrientationState() {
    throw UnimplementedError(
        'getOrientationState has not been implemented.');
  }
}