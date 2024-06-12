/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

import 'package:flutter/services.dart';
import 'package:linfo_pl/src/window/domain/sources/orientation_state_protocol.dart';

import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../../util/orientation_state_protocol_parse.dart';
import '../services/window_orientation_platform_interface.dart';

class WindowOrientationChannelController implements WindowOrientationPlatformInterface {
  static Stream<OrientationStateProtocol>? _orientationEvent;
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  static const EventChannel _channel = MethodChannelUtil.orientationEventChannel;

  @override
  Stream<OrientationStateProtocol> orientationEventStream() {
    _orientationEvent ??= _channel
        .receiveBroadcastStream().map((event) {
      LoggingUtil.info('Orientation Event Cast: $event');
      return parseOrientationStateProtocols(event);
    });

    return _orientationEvent!;
  }

  @override
  Future<OrientationStateProtocol> getOrientationState() async {
    OrientationStateProtocol result = await _methodChannel
        .invokeMethod<int?>('orientationState')
        .then((value) => parseOrientationStateProtocols(value));
    LoggingUtil.info('CHECK ORIENTATION STATE: ${result.name}');

    return result;
  }
}