import 'package:flutter_test/flutter_test.dart';
import 'package:linfo_pl/linfo_pl.dart';
import 'package:linfo_pl/linfo_pl_platform_interface.dart';
import 'package:linfo_pl/linfo_pl_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockLinfoPlPlatform
    with MockPlatformInterfaceMixin
    implements LinfoPlPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final LinfoPlPlatform initialPlatform = LinfoPlPlatform.instance;

  test('$MethodChannelLinfoPl is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelLinfoPl>());
  });

  test('getPlatformVersion', () async {
    LinfoPl linfoPlPlugin = LinfoPl();
    MockLinfoPlPlatform fakePlatform = MockLinfoPlPlatform();
    LinfoPlPlatform.instance = fakePlatform;

    expect(await linfoPlPlugin.getPlatformVersion(), '42');
  });
}
