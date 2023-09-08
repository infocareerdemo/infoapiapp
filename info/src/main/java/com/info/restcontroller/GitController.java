package com.info.restcontroller;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitController {

	@Value("${repositoryUrl}")
	String repositoryUrl;
	@Value("${destinationPath}")
	String destinationPath;
	@Value("${username}")
	String username;
	@Value("${password}")
	String password;
	@Value("${repositoryPath}")
	String repositoryPath;
	@Value("${targetFolderPath}")
	String targetFolderPath;
	@Value("${commitMessage}")
	String commitMessage;

	@PostMapping("/clone")
	public String cloneGitRepository() {
		return cloneRepositoryWithAuthentication(repositoryUrl, destinationPath, username, password);
	}

	public String cloneRepositoryWithAuthentication(String repoURL, String filePath, String username, String password) {
		try {
			CloneCommand cloneCommand = Git.cloneRepository().setURI(repoURL).setDirectory(new File(filePath));
			cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
			Git git = cloneCommand.call();
			git.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Git repo clone completed";
	}

	@PostMapping("/pull")
	public String pullChangesToSpecificFolder() {
		return pullChangesToFolder(repositoryPath, targetFolderPath, username, password);
	}

	public String pullChangesToFolder(String repoURL, String filePath, String username, String password) {

		try {
			File gitDir = new File(repoURL);
			Git git = Git.open(gitDir);
			PullCommand pullCommand = git.pull();
			pullCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
			pullCommand.call();
			File targetFolder = new File(filePath);
			FileUtils.copyDirectory(new File(repoURL), targetFolder);
			git.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "Changes Pull From Repository Completed";
	}

	@PostMapping("/commit")
	public String makeChangesAndCommit() {
		return commitChanges(repositoryPath, username, password, commitMessage);

	}

	public String commitChanges(String repoURL, String username, String password, String commitMessage) {

		try {
			File gitDir = new File(repoURL);
			Git git = Git.open(gitDir);
			git.add().addFilepattern(".").call();
			git.commit().setAuthor(username, "").setMessage(commitMessage).call();
			git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
			git.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Commited to git repo";

	}

}