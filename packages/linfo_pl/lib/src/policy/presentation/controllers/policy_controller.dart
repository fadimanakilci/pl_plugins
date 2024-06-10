/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

import '../../business/services/policy_platform_interface.dart';

class PolicyController implements PolicyPlatformInterface {
  static PolicyController? _singleton;

  PolicyController._();

  factory PolicyController() => _singleton ??= PolicyController._();

  static PolicyPlatformInterface get _platform => PolicyPlatformInterface.instance;

  @override
  Future<bool?> turnOffScreen() {
    return _platform.turnOffScreen();
  }
}