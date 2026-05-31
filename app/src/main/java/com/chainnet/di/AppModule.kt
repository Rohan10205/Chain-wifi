package com.chainnet.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import androidx.room.Room
import com.chainnet.data.datastore.ConfigDataStore
import com.chainnet.data.db.ChainNetDatabase
import com.chainnet.data.repository.PeerRepositoryImpl
import com.chainnet.data.repository.RoutingRepositoryImpl
import com.chainnet.data.repository.TopologyRepositoryImpl
import com.chainnet.domain.repository.NetworkRepository
import com.chainnet.domain.repository.PeerRepository
import com.chainnet.domain.repository.RoutingRepository
import com.chainnet.domain.repository.TopologyRepository
import com.chainnet.domain.usecase.DiscoverPeersUseCase
import com.chainnet.domain.usecase.FormChainUseCase
import com.chainnet.domain.usecase.HealNetworkUseCase
import com.chainnet.domain.usecase.ObserveTopologyUseCase
import com.chainnet.domain.usecase.RoutePacketUseCase
import com.chainnet.network.control.ControlPlaneServer
import com.chainnet.network.discovery.BleBootstrapper
import com.chainnet.network.discovery.NsdDiscovery
import com.chainnet.network.discovery.QrCodeManager
import com.chainnet.network.discovery.WifiP2pDiscovery
import com.chainnet.network.repository.NetworkRepositoryImpl
import com.chainnet.network.routing.AodvRoutingEngine
import com.chainnet.network.routing.NatConfigurator
import com.chainnet.network.security.CryptoManager
import com.chainnet.network.wifi.SoftApManager
import com.chainnet.network.wifi.StaConnectionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWifiManager(@ApplicationContext context: Context): WifiManager {
        return context.getSystemService(WifiManager::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(ConnectivityManager::class.java)
    }

    @Provides
    @Singleton
    fun provideNsdManager(@ApplicationContext context: Context): NsdManager {
        return context.getSystemService(NsdManager::class.java)
    }

    @Provides
    @Singleton
    fun provideWifiP2pManager(@ApplicationContext context: Context): WifiP2pManager {
        return context.getSystemService(WifiP2pManager::class.java)
    }

    @Provides
    @Singleton
    fun provideBluetoothAdapter(@ApplicationContext context: Context): BluetoothAdapter? {
        val manager = context.getSystemService(BluetoothManager::class.java)
        return manager?.adapter
    }

    @Provides
    @Singleton
    fun provideSoftApManager(wifiManager: WifiManager): SoftApManager {
        return SoftApManager(wifiManager)
    }

    @Provides
    @Singleton
    fun provideStaConnectionManager(connectivityManager: ConnectivityManager): StaConnectionManager {
        return StaConnectionManager(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(
        softApManager: SoftApManager,
        staConnectionManager: StaConnectionManager
    ): NetworkRepository {
        return NetworkRepositoryImpl(softApManager, staConnectionManager)
    }

    @Provides
    @Singleton
    fun provideControlPlaneServer(): ControlPlaneServer = ControlPlaneServer()

    @Provides
    @Singleton
    fun provideWifiP2pDiscovery(
        @ApplicationContext context: Context,
        manager: WifiP2pManager
    ): WifiP2pDiscovery {
        return WifiP2pDiscovery.create(context, manager)
    }

    @Provides
    @Singleton
    fun provideNsdDiscovery(nsdManager: NsdManager): NsdDiscovery = NsdDiscovery(nsdManager)

    @Provides
    @Singleton
    fun provideBleBootstrapper(adapter: BluetoothAdapter?): BleBootstrapper = BleBootstrapper(adapter)

    @Provides
    @Singleton
    fun provideQrCodeManager(): QrCodeManager = QrCodeManager()

    @Provides
    @Singleton
    fun provideNatConfigurator(): NatConfigurator = NatConfigurator()

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager = CryptoManager()

    @Provides
    @Singleton
    fun provideRoutingEngine(routingRepository: RoutingRepository): AodvRoutingEngine {
        return AodvRoutingEngine(routingRepository)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ChainNetDatabase {
        return Room.databaseBuilder(context, ChainNetDatabase::class.java, "chainnet.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePeerDao(db: ChainNetDatabase) = db.peerDao()

    @Provides
    fun provideEdgeDao(db: ChainNetDatabase) = db.topologyEdgeDao()

    @Provides
    fun provideRouteDao(db: ChainNetDatabase) = db.routeDao()

    @Provides
    @Singleton
    fun providePeerRepository(peerDao: com.chainnet.data.db.dao.PeerDao): PeerRepository {
        return PeerRepositoryImpl(peerDao)
    }

    @Provides
    @Singleton
    fun provideTopologyRepository(
        peerDao: com.chainnet.data.db.dao.PeerDao,
        edgeDao: com.chainnet.data.db.dao.TopologyEdgeDao
    ): TopologyRepository {
        return TopologyRepositoryImpl(peerDao, edgeDao)
    }

    @Provides
    @Singleton
    fun provideRoutingRepository(routeDao: com.chainnet.data.db.dao.RouteDao): RoutingRepository {
        return RoutingRepositoryImpl(routeDao)
    }

    @Provides
    @Singleton
    fun provideConfigDataStore(@ApplicationContext context: Context): ConfigDataStore {
        return ConfigDataStore.create(context)
    }

    @Provides
    fun provideDiscoverPeersUseCase(peerRepository: PeerRepository): DiscoverPeersUseCase {
        return DiscoverPeersUseCase(peerRepository)
    }

    @Provides
    fun provideFormChainUseCase(networkRepository: NetworkRepository): FormChainUseCase {
        return FormChainUseCase(networkRepository)
    }

    @Provides
    fun provideHealNetworkUseCase(networkRepository: NetworkRepository): HealNetworkUseCase {
        return HealNetworkUseCase(networkRepository)
    }

    @Provides
    fun provideObserveTopologyUseCase(topologyRepository: TopologyRepository): ObserveTopologyUseCase {
        return ObserveTopologyUseCase(topologyRepository)
    }

    @Provides
    fun provideRoutePacketUseCase(routingRepository: RoutingRepository): RoutePacketUseCase {
        return RoutePacketUseCase(routingRepository)
    }
}
