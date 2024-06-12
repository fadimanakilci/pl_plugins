/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/window/business/controllers/window_channel_controller.dart';
import 'package:linfo_pl/src/window/domain/sources/keyguard_status_protocol.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../domain/models/window_model.dart';

interface class WindowPlatformInterface extends PlatformInterface {
  WindowPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static WindowPlatformInterface _instance = WindowChannelController();

  static WindowPlatformInterface get instance => _instance;

  static set instance(WindowPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Stream<WindowModel?> screenEventStream() {
    throw UnimplementedError(
        'screenEventStream has not been implemented.');
  }

  Future<bool> keepScreenOn() {
    throw UnimplementedError(
        'keepScreenOn has not been implemented.');
  }

  Future<bool> discardScreenOn() {
    throw UnimplementedError(
        'discardScreenOn has not been implemented.');
  }

  Future<bool> getScreenOn() {
    throw UnimplementedError(
        'getScreenOn has not been implemented.');
  }

  Future<KeyguardStatusProtocol> requestKeyguard() {
    throw UnimplementedError(
        'requestKeyguard has not been implemented.');
  }

  Future<bool> changeBrightness({double? brightness}) {
    throw UnimplementedError(
        'changeBrightness has not been implemented.');
  }
}