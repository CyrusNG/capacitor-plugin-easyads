export interface EasyAdsPlugin {
  init(config: Config): Promise<Result>;
  splash(mode: string): Promise<Result>;
  banner(name: string): Promise<Result>;
  nativeExpress(name: string): Promise<Result>;
  rewardVideo(name: string): Promise<Result>;
  nativeExpressRecyclerView(name: string): Promise<Result>;
  interstitial(name: string): Promise<Result>;
  fullVideo(name: string): Promise<Result>;
  draw(name: string): Promise<Result>;
  customChannel(name: string): Promise<Result>;
}

export interface Config {
  rules: Array<{ tag: string, sort: Array<number>, percent: number }>, 
  apps: Array<{ tag: string, appId: string, index: number }>, 
  adspots: Array<{ tag: string, targets: Array<string> }>,

}

export interface Result {
  code: string,
  data: Object,
  error?: Object
}
