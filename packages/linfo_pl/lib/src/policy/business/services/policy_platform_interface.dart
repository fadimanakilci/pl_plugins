/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */


import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../controllers/policy_channel_controller.dart';

interface class PolicyPlatformInterface extends PlatformInterface {
  PolicyPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static PolicyPlatformInterface _instance = PolicyChannelController();

  static PolicyPlatformInterface get instance => _instance;

  static set instance(PolicyPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool?> turnOffScreen() {
    throw UnimplementedError(
        'turnOffScreen has not been implemented.');
  }
}