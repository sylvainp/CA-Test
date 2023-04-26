package com.sylvainp.ca_test.domain.ports

import com.sylvainp.ca_test.adapters.secondaries.RemoteBankAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BankModule {

    @Singleton
    @Binds
    abstract fun bindBankPort(
        bankAdapter: RemoteBankAdapter
        // change from RemoteBankAdapter to LocalBankAdapter if you want to load banks from assets
    ): BankPort
}