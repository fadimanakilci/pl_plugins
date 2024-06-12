/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/services.dart';
import 'package:linfo_pl/src/window/domain/sources/keyguard_status_protocol.dart';
import 'package:linfo_pl/src/window/util/keyguard_status_protocol_parse.dart';

import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../../domain/models/window_model.dart';
import '../services/window_platform_interface.dart';

class WindowChannelController implements WindowPlatformInterface {
  static Stream<WindowModel?>? _windowEvent;
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  static const EventChannel _channel = MethodChannelUtil.windowEventChannel;

  @override
  Stream<WindowModel?> screenEventStream() {
    _windowEvent ??= _channel
        .receiveBroadcastStream().map((event) {
      LoggingUtil.info('Window Event Cast: ${_windowEvent.toString()} - ${_windowEvent.runtimeType}');
      return WindowModel.fromMap(event);
    });

    return _windowEvent!;
  }

  @override
  Future<bool> keepScreenOn() async {
    bool result = await _methodChannel
        .invokeMethod<bool>('keepScreenOn')
        .then((value) => value!);

    LoggingUtil.info('CHECK WINDOW KEEP STATUS: $result');

    return result;
  }

  @override
  Future<bool> discardScreenOn() async {
    bool result = await _methodChannel
        .invokeMethod<bool>('discardScreenOn')
        .then((value) => value!);

    LoggingUtil.info('CHECK WINDOW DISCARD STATUS: $result');

    return result;
  }

  @override
  Future<bool> getScreenOn() async {
    bool result = await _methodChannel
        .invokeMethod<bool>('screenOn')
        .then((value) => value!);

    LoggingUtil.info('CHECK WINDOW SCREEN ON: $result');

    return result;
  }

  @override
  Future<KeyguardStatusProtocol> requestKeyguard() async {
    KeyguardStatusProtocol result = await _methodChannel
        .invokeMethod<int?>('requestKeyguard')
        .then((value) => parseKeyguardStatusProtocols(value));

    LoggingUtil.info('CHECK WINDOW REQUEST KEYGUARD: $result');

    return result;
  }

  @override
  Future<bool> changeBrightness({double? brightness}) async {
    Map<String, dynamic> arguments = {
      'brightness': brightness,
    };

    bool result = await _methodChannel
        .invokeMethod<bool>('changeBrightness', arguments)
        .then((value) => value!);

    LoggingUtil.info('CHECK CHANGE BRIGHTNESS: $result');

    return result;
  }

}