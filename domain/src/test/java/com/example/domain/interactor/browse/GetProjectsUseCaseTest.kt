package com.example.domain.interactor.browse

import com.example.domain.executor.PostExecutionThread
import com.example.domain.model.Project
import com.example.domain.repository.ProjectsRepository
import com.example.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class GetProjectsUseCaseTest     {

    private var projectsRepository: ProjectsRepository = mock()
    private var postExecutionThread: PostExecutionThread = mock()
    private var useCase = GetProjectsUseCase(projectsRepository, postExecutionThread)

    @Test
    fun `WHEN use case invoked EXPECT it completes`() {
        whenever(projectsRepository.getProjects()).thenReturn(Observable.just(ProjectDataFactory.makeProjectList(2)))
        useCase.buildUseCaseObservable()
            .test()
            .assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(300)
        whenever(projectsRepository.getProjects()).thenReturn(Observable.just(projects))
        useCase.buildUseCaseObservable()
            .test()
            .assertValues(projects)
    }
}