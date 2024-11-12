import { registerPlugin } from '@capacitor/core';

import type { EasyAdsPlugin } from './definitions';

const EasyAds = registerPlugin<EasyAdsPlugin>('EasyAds', {
  web: () => import('./web').then((m) => new m.EasyAdsWeb()),
});

export * from './definitions';
export { EasyAds };
