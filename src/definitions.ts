export interface EasyAdsPlugin {
  init(): Promise<Result>;
  splash(mode: string, config: Config): Promise<Result>;
  banner(config: Config): Promise<Result>;
  nativeExpress(config: Config): Promise<Result>;
  rewardVideo(config: Config): Promise<Result>;
  nativeExpressRecyclerView(config: Config): Promise<Result>;
  interstitial(config: Config): Promise<Result>;
  fullVideo(config: Config): Promise<Result>;
  draw(config: Config): Promise<Result>;
  customChannel(config: Config): Promise<Result>;
}

export interface Config {
  rules: Array<{ tag: string, sort: Array<number>, percent: number }>, 
  suppliers: Array<{ tag: string, adspotId: string, appId: string, index: number }>
}

export interface Result {
  code: string,
  data: Object,
  error?: Object
}
