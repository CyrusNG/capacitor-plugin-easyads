import { WebPlugin } from '@capacitor/core';

import type { EasyAdsPlugin, Config, Result } from './definitions';

export class EasyAdsWeb extends WebPlugin implements EasyAdsPlugin {

  async init(config: Config): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async splash(mode: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async banner(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async nativeExpress(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async rewardVideo(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async nativeExpressRecyclerView(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async interstitial(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async fullVideo(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async splashDialog(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async draw(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

  async customChannel(name: string): Promise<Result> {
    throw this.unimplemented('Not implemented on web.');
  }

}
