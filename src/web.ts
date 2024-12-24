import { WebPlugin } from '@capacitor/core';

import type { EasyAdsPlugin, Config, Result } from './definitions';

export class EasyAdsWeb extends WebPlugin implements EasyAdsPlugin {

  async init(config: Config): Promise<Result> {
    console.log(`init(${config})`);
    throw this.unimplemented('init() API is not implemented on web.');
  }

  async load(type: string, tag: string): Promise<Result> {
    console.log(`load(${type}, ${tag})`);
    throw this.unimplemented('load() API is not implemented on web.');
  }

  async destroy(callId: string): Promise<Result> {
    console.log(`destroy(${callId})`);
    throw this.unimplemented('destroy() API is not implemented on web.');
  }

}
