# uos
'basic “operating system” services'
from typing import Iterator, Tuple, Any


def chdir(path):
    "Change current directory."
    pass


def getcwd() -> str:
    "Get the current directory."
    pass


def ilistdir(dir: str = None) -> Iterator[Tuple[str, int, int]]:
    """This function returns an iterator which then yields 3-tuples corresponding to the entries in the directory that it is listing. With no argument it lists the current directory, otherwise it lists the directory given by dir.

    The 3-tuples have the form (name, type, inode):

    name is a string (or bytes if dir is a bytes object) and is the name of the entry;
    type is an integer that specifies the type of the entry, with 0x4000 for directories and 0x8000 for regular files;
    inode is an integer corresponding to the inode of the file, and may be 0 for filesystems that don’t have such a notion.
    """
    pass


def listdir(dir: str = None):
    "With no argument, list the current directory. Otherwise list the given directory."
    pass


def mkdir(path: str):
    "Create a new directory."
    pass


def remove(path: str):
    "Remove a file."
    pass


def rmdir(path: str):
    "Remove a directory."
    pass


def rename(old_path: str, new_path: str):
    "Rename a file."
    pass


def stat(path: str) -> Tuple:
    "Get the status of a file or directory."
    pass


def statvfs(path: str) -> Tuple[int, int, int, int, int, int, int, int, int, int]:
    """Get the status of a fileystem.

    Returns a tuple with the filesystem information in the following order:

    f_bsize – file system block size
    f_frsize – fragment size
    f_blocks – size of fs in f_frsize units
    f_bfree – number of free blocks
    f_bavail – number of free blocks for unpriviliged users
    f_files – number of inodes
    f_ffree – number of free inodes
    f_favail – number of free inodes for unpriviliged users
    f_flag – mount flags
    f_namemax – maximum filename length
    Parameters related to inodes: f_files, f_ffree, f_avail and the f_flags parameter may return 0 as they can be unavailable in a port-specific implementation.
    """
    pass


def sync():
    "Sync all filesystems."
    pass


def urandom(n: int) -> bytes:
    "Return a bytes object with n random bytes. Whenever possible, it is generated by the hardware random number generator."
    pass


def dupterm(stream_object: Any, index: int = 0):
    """Duplicate or switch the MicroPython terminal (the REPL) on the given stream-like object. The stream_object argument must implement the readinto() and write() methods. The stream should be in non-blocking mode and readinto() should return None if there is no data available for reading.
    After calling this function all terminal output is repeated on this stream, and any input that is available on the stream is passed on to the terminal input.
    The index parameter should be a non-negative integer and specifies which duplication slot is set. A given port may implement more than one slot (slot 0 will always be available) and in that case terminal input and output is duplicated on all the slots that are set.
    If None is passed as the stream_object then duplication is cancelled on the slot given by index.
    The function returns the previous stream-like object in the given slot."""
    pass


def mount(fsobj: Any, mount_point: str, *, readonly: bool = False):
    """Mount the filesystem object fsobj at the location in the VFS given by the mount_point string. fsobj can be a a VFS object that has a mount() method, or a block device. If it’s a block device then the filesystem type is automatically detected (an exception is raised if no filesystem was recognised). mount_point may be '/' to mount fsobj at the root, or '/<name>' to mount it at a subdirectory under the root.
    If readonly is True then the filesystem is mounted read-only.
    During the mount process the method mount() is called on the filesystem object.
    Will raise OSError(EPERM) if mount_point is already mounted.
    """
    pass


def umount(mount_point: str):
    """Unmount a filesystem. mount_point can be a string naming the mount location, or a previously-mounted filesystem object. During the unmount process the method umount() is called on the filesystem object.
    Will raise OSError(EINVAL) if mount_point is not found.
    """
    pass