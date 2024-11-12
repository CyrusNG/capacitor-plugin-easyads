import { WebPlugin } from '@capacitor/core';

import type { EasyAdsPlugin } from './definitions';

export class EasyAdsWeb extends WebPlugin implements EasyAdsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
