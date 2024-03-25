import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:info_pl/src/business/controllers/info_pl_method_channel_controller.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  InfoPlMethodChannelController platform = InfoPlMethodChannelController();
  const MethodChannel channel = MethodChannel('com.sparksign.info_pl/methods');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return null;
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('init', () async {
    expect(await platform.init(), null);
  });
}
