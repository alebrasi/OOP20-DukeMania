package it.dukemania.Controller.filedialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

//TODO Call it maybe JavaNioFileDialogControllerImpl
public class FileDialogControllerImpl implements FileDialogController {

    private String rootDirectory;
    private final String directorySeparator;
    private Pattern regexFilter;
    private final Pattern removeLastDirectoryRegex;
    private String currentDirectory;
    private FileSelectedListener fileSelected;


    public FileDialogControllerImpl(final String filter) {
        this.directorySeparator = File.separator;
        this.rootDirectory = System.getProperty("user.home") + directorySeparator;
        this.currentDirectory = this.rootDirectory;
        this.setFilter(filter);
        //String removeLastDirectoryStrRegex = String.format("^.+%s\\w+%s", this.directorySeparator, this.directorySeparator);
        //TODO Update regex
        String removeLastDirectoryStrRegex = String.format(".+\\%s", this.directorySeparator);
        System.out.println(removeLastDirectoryStrRegex);
        removeLastDirectoryRegex = Pattern.compile(removeLastDirectoryStrRegex);
    }

    public FileDialogControllerImpl() {
        this("\\\\*.*");
    }

    @Override
    public void setFilter(final String regexFilter) throws PatternSyntaxException {
        this.regexFilter = Pattern.compile(regexFilter);
    }

    @Override
    public void stepBackFromCurrentDirectory() {
        System.out.println(this.currentDirectory);
        Matcher matcher = removeLastDirectoryRegex.matcher(this.currentDirectory.substring(0, this.currentDirectory.length() - 1));
        if (matcher.find()) {
            this.currentDirectory = matcher.group();
        }
    }

    private FileInfo createFileInfo(final File file) {
        return new FileInfo(file.getName(), file.isDirectory() ? FileType.DIRECTORY : FileType.REGULAR_FILE);
    }

    @Override
    public List<FileInfo> getFilesInCurrentDirectory() {
        try {
            return Files.list(Paths.get(this.currentDirectory))
                    .map(Path::toFile)
                    .filter(file -> !file.isHidden())
                    .filter(file -> regexFilter.matcher(file.getName()).find() || file.isDirectory())
                    .map(this::createFileInfo)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public String getDirectorySeparator() {
        return this.directorySeparator;
    }

    @Override
    public void returnToRootDirectory() {
        this.currentDirectory = this.rootDirectory;
    }

    @Override
    public boolean advanceInDirectory(final String directoryName) {
        if (Files.isDirectory(Paths.get(this.currentDirectory + directoryName))) {
            this.currentDirectory += directoryName;
            return true;
        }

        if (fileSelected != null) {
            fileSelected.selectedFile(directoryName, this.currentDirectory + directoryName);
        }

        return false;
    }

    @Override
    public void setSelectedFileListener(final FileSelectedListener listener) {
        fileSelected = listener;
    }

    @Override
    public void setRootDirectory(final String path) throws NotDirectoryException {
        if (Paths.get(path).toFile().isDirectory()) {
            this.rootDirectory = path;
        } else {
            throw new NotDirectoryException(path);
        }
    }
}
