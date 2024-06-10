/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'dart:async';

import 'package:flutter/services.dart';
import 'package:linfo_pl/src/activity_lifecycle/business/services/activity_lifecycle_platform_interface.dart';

import '../../../../activity_lifecycle.dart';
import '../../utils/activity_lifecycle_protocol_parse_util.dart';
import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';

class ActivityLifecycleChannelController extends ActivityLifecyclePlatformInterface {
  final Completer<ActivityLifecycleProtocol> _completer = Completer();
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  final StreamController<ActivityLifecycleProtocol> _lifecycleStreamController
  = StreamController<ActivityLifecycleProtocol>();

  @override
  Stream<ActivityLifecycleProtocol> activityLifecycleEventStream() {
    _methodChannel.setMethodCallHandler((call) async {
      if(call.method == 'lifecycle') {
        // switch(call.arguments) {
        //   case 'created':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.created);
        //     break;
        //   case 'started':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.started);
        //     break;
        //   case 'resumed':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.resumed);
        //     break;
        //   case 'paused':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.paused);
        //     break;
        //   case 'stopped':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.stopped);
        //     break;
        //   case 'saveInstanceState':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.saveInstanceState);
        //     break;
        //   case 'destroyed':
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.destroyed);
        //     break;
        //   default:
        //     _lifecycleStreamController.add(ActivityLifecycleProtocol.unknown);
        // }

        try {
          _lifecycleStreamController.add(
              parseActivityLifecycleProtocols(call.arguments));
        } catch(e) {
          LoggingUtil.warning(e.toString());
          _lifecycleStreamController.add(ActivityLifecycleProtocol.unknown);
        }
      } else {
        _lifecycleStreamController.add(ActivityLifecycleProtocol.unknown);
      }
    });
    return _lifecycleStreamController.stream;
  }

  @override
  Future<ActivityLifecycleProtocol> getActivityLifecycleEvent() async {
    // _methodChannel.setMethodCallHandler((call) async {
    //   if(call.method == 'lifecycle') {
    //     switch(call.arguments) {
    //       case 'create':
    //         _completer.complete(ActivityLifecycleProtocol.created);
    //         break;
    //       case 'start':
    //         _completer.complete(ActivityLifecycleProtocol.started);
    //         break;
    //       case 'resume':
    //         print("Girdiiiiiiiğğğğğğğğğğğ");
    //         _completer.complete(ActivityLifecycleProtocol.resumed);
    //         break;
    //       case 'pause':
    //         _completer.complete(ActivityLifecycleProtocol.paused);
    //         break;
    //       case 'stop':
    //         _completer.complete(ActivityLifecycleProtocol.stopped);
    //         break;
    //       case 'saveInstanceState':
    //         _completer.complete(ActivityLifecycleProtocol.saveInstanceState);
    //         break;
    //       case 'destroy':
    //         _completer.complete(ActivityLifecycleProtocol.destroyed);
    //         break;
    //       default:
    //         _completer.complete(ActivityLifecycleProtocol.unknown);
    //     }
    //   } else {
    //     _completer.complete(ActivityLifecycleProtocol.unknown);
    //   }
    // });
    // return _completer.future;
    ActivityLifecycleProtocol state = parseActivityLifecycleProtocols(
        (await _methodChannel.invokeMethod<String>('activityLifecycle'))!);

    LoggingUtil.info('ACTIVITY LIFECYCLE STATE: $state');

    return state;
  }
}