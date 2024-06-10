/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

import 'package:linfo_pl/src/policy/presentation/controllers/policy_controller.dart';

final _policy = PolicyController();

@override
Future<bool?> turnOffScreen() {
  return _policy.turnOffScreen();
}