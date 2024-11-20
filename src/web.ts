import { WebPlugin } from '@capacitor/core';

import type { EasyAdsPlugin, Config, Result } from './definitions';

export class EasyAdsWeb extends WebPlugin implements EasyAdsPlugin {

  async init(): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async splash(mode: string, config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async banner(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async nativeExpress(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async rewardVideo(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async nativeExpressRecyclerView(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async interstitial(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async fullVideo(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async splashDialog(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async draw(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async customChannel(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

}
