import { WebPlugin } from '@capacitor/core';

import type { EasyAdsPlugin, Config, Result } from './definitions';

export class EasyAdsWeb extends WebPlugin implements EasyAdsPlugin {

  async init(): Promise<Result> {
    return { code: '1', data: {} };
  }

  async banner(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async splash(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async nativeExpress(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async rewardVideo(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async nativeExpressRecyclerView(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async interstitial(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async fullVideo(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async splashDialog(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async draw(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

  async customChannel(config: Config): Promise<Result> {
    console.log('CONFIG', config);
    return { code: '1', data: {} };
  }

}
